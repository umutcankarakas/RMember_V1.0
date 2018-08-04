package retrofit.android.vogella.com.rmember;

import android.arch.lifecycle.ViewModelProviders;
import android.arch.lifecycle.ViewModelStoreOwner;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit.android.vogella.com.rmember.Database.TaskRepository;
import retrofit.android.vogella.com.rmember.Interface.LoginResultCallbacks;
import retrofit.android.vogella.com.rmember.Local.TaskDataSource;
import retrofit.android.vogella.com.rmember.Local.TaskDatabase;
import retrofit.android.vogella.com.rmember.Model.Task;
import retrofit.android.vogella.com.rmember.ViewModel.LoginViewModel;
import retrofit.android.vogella.com.rmember.ViewModel.LoginViewModelFactory;
import retrofit.android.vogella.com.rmember.databinding.ActivityMainBinding;

public class EventListActivity extends AppCompatActivity {

    private ListView lstTask;
    private FloatingActionButton fab;

    //Adapter
    List<Task> taskList = new ArrayList<>();
    ArrayAdapter adapter;


    //Database
    private CompositeDisposable compositeDisposable;
    private TaskRepository taskRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_event);

        //Init
        compositeDisposable = new CompositeDisposable();

        lstTask = (ListView)findViewById(R.id.lstTasks);
        fab = (FloatingActionButton) findViewById (R.id.fab);

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, taskList);
        registerForContextMenu(lstTask);
        lstTask.setAdapter(adapter);

        //Database
        TaskDatabase taskDatabase = TaskDatabase.getmInstance(this); //Create database
        taskRepository = TaskRepository.getInstance(TaskDataSource.getInstance(taskDatabase.taskDAO()));

        //Load all data from Database
        loadData();

        //Event
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add new task
                //Random email
                 Disposable disposable = io.reactivex.Observable.create(new ObservableOnSubscribe<Object>() {
                     @Override
                     public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                         Task task = new Task("MyTask",
                                 UUID.randomUUID().toString()+" Added");
                         taskRepository.insertTask(task);
                         emitter.onComplete();
                     }
                 })
                        .observeOn(AndroidSchedulers.mainThread())
                         .subscribeOn(Schedulers.io())
                         .subscribe(new Consumer() {
                                        @Override
                                        public void accept(Object o) throws Exception {
                                            Toast.makeText(EventListActivity.this, "Task added !", Toast.LENGTH_SHORT).show();
                                        }
                                    }, new Consumer<Throwable>() {
                                        @Override
                                        public void accept(Throwable throwable) throws Exception {
                                            Toast.makeText(EventListActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    },
                                 new Action() {
                                     @Override
                                     public void run() throws Exception {
                                         loadData(); //Refresh data
                                     }
                                 }

                         );
            }
        });

    }

    private void loadData() {
        //Use RxJava
        Disposable disposable = taskRepository.getAllTasks()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Task>>() {
                    @Override
                    public void accept(List<Task> tasks) throws Exception {
                        onGetAllTasksSuccess(tasks);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(EventListActivity.this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        compositeDisposable.add(disposable);
    }

    private void onGetAllTasksSuccess(List<Task> tasks) {
        taskList.clear();
        taskList.addAll(tasks);
        adapter.notifyDataSetChanged();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_clear:
                deleteAllTasks();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteAllTasks() {
        Disposable disposable = io.reactivex.Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                taskRepository.deleteAllTasks();
                emitter.onComplete();
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer() {
                               @Override
                               public void accept(Object o) throws Exception {

                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   Toast.makeText(EventListActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                               }
                           },
                        new Action() {
                            @Override
                            public void run() throws Exception {
                                loadData(); //Refresh data
                            }
                        }

                );
        compositeDisposable.add(disposable);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        menu.setHeaderTitle("Select action: ");

        menu.add(Menu.NONE, 0, Menu.NONE, "UPDATE");
        menu.add(Menu.NONE, 0, Menu.NONE, "DELETE");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        final Task task = taskList.get(info.position);
        switch (item.getItemId()){
            case 0: //update
            {
                final EditText edtName = new EditText(EventListActivity.this);
                edtName.setText(task.getTaskTitle());
                edtName.setHint("Enter task title");
                new AlertDialog.Builder(EventListActivity.this)
                        .setTitle("Edit")
                        .setMessage("Edit task title")
                        .setView(edtName)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(TextUtils.isEmpty(edtName.getText().toString()))
                                    return;
                                else{
                                    task.setTaskTitle(edtName.getText().toString());
                                    updateTask(task);
                                }
                            }
                        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
            }
            break;
            case 1: //Delete
            {
                new AlertDialog.Builder(EventListActivity.this)
                        .setMessage("Do you want to delete " + task.toString())
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteTask(task);
                            }
                        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
            }
            break;
        }
        return true;
    }

    private void deleteTask(final Task task){
        Disposable disposable = io.reactivex.Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                taskRepository.deleteTask(task);
                emitter.onComplete();
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer() {
                               @Override
                               public void accept(Object o) throws Exception {

                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   Toast.makeText(EventListActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                               }
                           },
                        new Action() {
                            @Override
                            public void run() throws Exception {
                                loadData(); //Refresh data
                            }
                        }

                );

        compositeDisposable.add(disposable);

    }



    private void updateTask(final Task task){
        Disposable disposable = io.reactivex.Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                taskRepository.updateTask(task);
                emitter.onComplete();
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer() {
                               @Override
                               public void accept(Object o) throws Exception {

                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   Toast.makeText(EventListActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                               }
                           },
                        new Action() {
                            @Override
                            public void run() throws Exception {
                                loadData(); //Refresh data
                            }
                        }

                );

        compositeDisposable.add(disposable);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}

package co.edu.eci.ieti.android.persistence;

import android.content.Context;

import java.util.List;

import co.edu.eci.ieti.android.model.Task;
import co.edu.eci.ieti.android.network.RetrofitNetwork;
import co.edu.eci.ieti.android.network.service.TaskService;

public class TaskRepository {

    private TaskDao taskDao;
    private TaskService taskService;

    public TaskRepository(String token, Context context){
        taskDao = AppDatabase.getDatabase(context).taskDao();
        taskService = new RetrofitNetwork(token).getTaskService();
    }

    public List<Task> getTasks(){
        try{
            List<Task> tasks = taskService.getTasks().execute().body();
            if(tasks!=null){
                taskDao.deleteAll();
                taskDao.insertAll(tasks);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return taskDao.getAll();
    }
}

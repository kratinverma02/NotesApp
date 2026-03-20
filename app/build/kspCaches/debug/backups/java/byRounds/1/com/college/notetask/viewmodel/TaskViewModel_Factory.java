package com.college.notetask.viewmodel;

import com.college.notetask.domain.usecase.DeleteTaskUseCase;
import com.college.notetask.domain.usecase.GetAllTasksUseCase;
import com.college.notetask.domain.usecase.GetTaskByIdUseCase;
import com.college.notetask.domain.usecase.SaveTaskUseCase;
import com.college.notetask.domain.usecase.ToggleTaskStatusUseCase;
import com.college.notetask.utils.ReminderScheduler;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class TaskViewModel_Factory implements Factory<TaskViewModel> {
  private final Provider<GetAllTasksUseCase> getAllTasksProvider;

  private final Provider<GetTaskByIdUseCase> getTaskByIdProvider;

  private final Provider<SaveTaskUseCase> saveTaskProvider;

  private final Provider<DeleteTaskUseCase> deleteTaskProvider;

  private final Provider<ToggleTaskStatusUseCase> toggleStatusProvider;

  private final Provider<ReminderScheduler> reminderSchedulerProvider;

  public TaskViewModel_Factory(Provider<GetAllTasksUseCase> getAllTasksProvider,
      Provider<GetTaskByIdUseCase> getTaskByIdProvider, Provider<SaveTaskUseCase> saveTaskProvider,
      Provider<DeleteTaskUseCase> deleteTaskProvider,
      Provider<ToggleTaskStatusUseCase> toggleStatusProvider,
      Provider<ReminderScheduler> reminderSchedulerProvider) {
    this.getAllTasksProvider = getAllTasksProvider;
    this.getTaskByIdProvider = getTaskByIdProvider;
    this.saveTaskProvider = saveTaskProvider;
    this.deleteTaskProvider = deleteTaskProvider;
    this.toggleStatusProvider = toggleStatusProvider;
    this.reminderSchedulerProvider = reminderSchedulerProvider;
  }

  @Override
  public TaskViewModel get() {
    return newInstance(getAllTasksProvider.get(), getTaskByIdProvider.get(), saveTaskProvider.get(), deleteTaskProvider.get(), toggleStatusProvider.get(), reminderSchedulerProvider.get());
  }

  public static TaskViewModel_Factory create(Provider<GetAllTasksUseCase> getAllTasksProvider,
      Provider<GetTaskByIdUseCase> getTaskByIdProvider, Provider<SaveTaskUseCase> saveTaskProvider,
      Provider<DeleteTaskUseCase> deleteTaskProvider,
      Provider<ToggleTaskStatusUseCase> toggleStatusProvider,
      Provider<ReminderScheduler> reminderSchedulerProvider) {
    return new TaskViewModel_Factory(getAllTasksProvider, getTaskByIdProvider, saveTaskProvider, deleteTaskProvider, toggleStatusProvider, reminderSchedulerProvider);
  }

  public static TaskViewModel newInstance(GetAllTasksUseCase getAllTasks,
      GetTaskByIdUseCase getTaskById, SaveTaskUseCase saveTask, DeleteTaskUseCase deleteTask,
      ToggleTaskStatusUseCase toggleStatus, ReminderScheduler reminderScheduler) {
    return new TaskViewModel(getAllTasks, getTaskById, saveTask, deleteTask, toggleStatus, reminderScheduler);
  }
}

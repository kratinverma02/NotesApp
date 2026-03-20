package com.college.notetask.utils;

import com.college.notetask.data.repository.TaskRepository;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class BootReceiver_MembersInjector implements MembersInjector<BootReceiver> {
  private final Provider<TaskRepository> taskRepositoryProvider;

  private final Provider<ReminderScheduler> reminderSchedulerProvider;

  public BootReceiver_MembersInjector(Provider<TaskRepository> taskRepositoryProvider,
      Provider<ReminderScheduler> reminderSchedulerProvider) {
    this.taskRepositoryProvider = taskRepositoryProvider;
    this.reminderSchedulerProvider = reminderSchedulerProvider;
  }

  public static MembersInjector<BootReceiver> create(
      Provider<TaskRepository> taskRepositoryProvider,
      Provider<ReminderScheduler> reminderSchedulerProvider) {
    return new BootReceiver_MembersInjector(taskRepositoryProvider, reminderSchedulerProvider);
  }

  @Override
  public void injectMembers(BootReceiver instance) {
    injectTaskRepository(instance, taskRepositoryProvider.get());
    injectReminderScheduler(instance, reminderSchedulerProvider.get());
  }

  @InjectedFieldSignature("com.college.notetask.utils.BootReceiver.taskRepository")
  public static void injectTaskRepository(BootReceiver instance, TaskRepository taskRepository) {
    instance.taskRepository = taskRepository;
  }

  @InjectedFieldSignature("com.college.notetask.utils.BootReceiver.reminderScheduler")
  public static void injectReminderScheduler(BootReceiver instance,
      ReminderScheduler reminderScheduler) {
    instance.reminderScheduler = reminderScheduler;
  }
}

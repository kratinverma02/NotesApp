package com.college.notetask.domain.usecase;

import com.college.notetask.data.repository.TaskRepository;
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
public final class GetAllTasksUseCase_Factory implements Factory<GetAllTasksUseCase> {
  private final Provider<TaskRepository> repositoryProvider;

  public GetAllTasksUseCase_Factory(Provider<TaskRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetAllTasksUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetAllTasksUseCase_Factory create(Provider<TaskRepository> repositoryProvider) {
    return new GetAllTasksUseCase_Factory(repositoryProvider);
  }

  public static GetAllTasksUseCase newInstance(TaskRepository repository) {
    return new GetAllTasksUseCase(repository);
  }
}

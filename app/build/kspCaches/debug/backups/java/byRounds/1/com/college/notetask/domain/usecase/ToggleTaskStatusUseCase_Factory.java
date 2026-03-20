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
public final class ToggleTaskStatusUseCase_Factory implements Factory<ToggleTaskStatusUseCase> {
  private final Provider<TaskRepository> repositoryProvider;

  public ToggleTaskStatusUseCase_Factory(Provider<TaskRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public ToggleTaskStatusUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static ToggleTaskStatusUseCase_Factory create(
      Provider<TaskRepository> repositoryProvider) {
    return new ToggleTaskStatusUseCase_Factory(repositoryProvider);
  }

  public static ToggleTaskStatusUseCase newInstance(TaskRepository repository) {
    return new ToggleTaskStatusUseCase(repository);
  }
}

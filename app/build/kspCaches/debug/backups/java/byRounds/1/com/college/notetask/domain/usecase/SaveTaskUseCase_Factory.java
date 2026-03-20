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
public final class SaveTaskUseCase_Factory implements Factory<SaveTaskUseCase> {
  private final Provider<TaskRepository> repositoryProvider;

  public SaveTaskUseCase_Factory(Provider<TaskRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public SaveTaskUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static SaveTaskUseCase_Factory create(Provider<TaskRepository> repositoryProvider) {
    return new SaveTaskUseCase_Factory(repositoryProvider);
  }

  public static SaveTaskUseCase newInstance(TaskRepository repository) {
    return new SaveTaskUseCase(repository);
  }
}

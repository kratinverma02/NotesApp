package com.college.notetask.domain.usecase;

import com.college.notetask.data.repository.NoteRepository;
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
public final class SaveNoteUseCase_Factory implements Factory<SaveNoteUseCase> {
  private final Provider<NoteRepository> repositoryProvider;

  public SaveNoteUseCase_Factory(Provider<NoteRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public SaveNoteUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static SaveNoteUseCase_Factory create(Provider<NoteRepository> repositoryProvider) {
    return new SaveNoteUseCase_Factory(repositoryProvider);
  }

  public static SaveNoteUseCase newInstance(NoteRepository repository) {
    return new SaveNoteUseCase(repository);
  }
}

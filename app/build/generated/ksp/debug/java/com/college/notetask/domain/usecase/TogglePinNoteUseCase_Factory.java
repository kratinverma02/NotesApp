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
public final class TogglePinNoteUseCase_Factory implements Factory<TogglePinNoteUseCase> {
  private final Provider<NoteRepository> repositoryProvider;

  public TogglePinNoteUseCase_Factory(Provider<NoteRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public TogglePinNoteUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static TogglePinNoteUseCase_Factory create(Provider<NoteRepository> repositoryProvider) {
    return new TogglePinNoteUseCase_Factory(repositoryProvider);
  }

  public static TogglePinNoteUseCase newInstance(NoteRepository repository) {
    return new TogglePinNoteUseCase(repository);
  }
}

package com.college.notetask.viewmodel;

import com.college.notetask.domain.usecase.DeleteNoteUseCase;
import com.college.notetask.domain.usecase.GetNoteByIdUseCase;
import com.college.notetask.domain.usecase.SaveNoteUseCase;
import com.college.notetask.domain.usecase.SearchNotesUseCase;
import com.college.notetask.domain.usecase.TogglePinNoteUseCase;
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
public final class NotesViewModel_Factory implements Factory<NotesViewModel> {
  private final Provider<SearchNotesUseCase> searchNotesProvider;

  private final Provider<GetNoteByIdUseCase> getNoteByIdProvider;

  private final Provider<SaveNoteUseCase> saveNoteProvider;

  private final Provider<DeleteNoteUseCase> deleteNoteProvider;

  private final Provider<TogglePinNoteUseCase> togglePinProvider;

  public NotesViewModel_Factory(Provider<SearchNotesUseCase> searchNotesProvider,
      Provider<GetNoteByIdUseCase> getNoteByIdProvider, Provider<SaveNoteUseCase> saveNoteProvider,
      Provider<DeleteNoteUseCase> deleteNoteProvider,
      Provider<TogglePinNoteUseCase> togglePinProvider) {
    this.searchNotesProvider = searchNotesProvider;
    this.getNoteByIdProvider = getNoteByIdProvider;
    this.saveNoteProvider = saveNoteProvider;
    this.deleteNoteProvider = deleteNoteProvider;
    this.togglePinProvider = togglePinProvider;
  }

  @Override
  public NotesViewModel get() {
    return newInstance(searchNotesProvider.get(), getNoteByIdProvider.get(), saveNoteProvider.get(), deleteNoteProvider.get(), togglePinProvider.get());
  }

  public static NotesViewModel_Factory create(Provider<SearchNotesUseCase> searchNotesProvider,
      Provider<GetNoteByIdUseCase> getNoteByIdProvider, Provider<SaveNoteUseCase> saveNoteProvider,
      Provider<DeleteNoteUseCase> deleteNoteProvider,
      Provider<TogglePinNoteUseCase> togglePinProvider) {
    return new NotesViewModel_Factory(searchNotesProvider, getNoteByIdProvider, saveNoteProvider, deleteNoteProvider, togglePinProvider);
  }

  public static NotesViewModel newInstance(SearchNotesUseCase searchNotes,
      GetNoteByIdUseCase getNoteById, SaveNoteUseCase saveNote, DeleteNoteUseCase deleteNote,
      TogglePinNoteUseCase togglePin) {
    return new NotesViewModel(searchNotes, getNoteById, saveNote, deleteNote, togglePin);
  }
}

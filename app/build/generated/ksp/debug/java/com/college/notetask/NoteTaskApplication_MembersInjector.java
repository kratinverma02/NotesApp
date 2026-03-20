package com.college.notetask;

import androidx.hilt.work.HiltWorkerFactory;
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
public final class NoteTaskApplication_MembersInjector implements MembersInjector<NoteTaskApplication> {
  private final Provider<HiltWorkerFactory> workerFactoryProvider;

  public NoteTaskApplication_MembersInjector(Provider<HiltWorkerFactory> workerFactoryProvider) {
    this.workerFactoryProvider = workerFactoryProvider;
  }

  public static MembersInjector<NoteTaskApplication> create(
      Provider<HiltWorkerFactory> workerFactoryProvider) {
    return new NoteTaskApplication_MembersInjector(workerFactoryProvider);
  }

  @Override
  public void injectMembers(NoteTaskApplication instance) {
    injectWorkerFactory(instance, workerFactoryProvider.get());
  }

  @InjectedFieldSignature("com.college.notetask.NoteTaskApplication.workerFactory")
  public static void injectWorkerFactory(NoteTaskApplication instance,
      HiltWorkerFactory workerFactory) {
    instance.workerFactory = workerFactory;
  }
}

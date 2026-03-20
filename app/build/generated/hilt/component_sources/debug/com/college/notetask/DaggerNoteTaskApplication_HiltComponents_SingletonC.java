package com.college.notetask;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.view.View;
import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.Preferences;
import androidx.fragment.app.Fragment;
import androidx.hilt.work.HiltWorkerFactory;
import androidx.hilt.work.WorkerAssistedFactory;
import androidx.hilt.work.WorkerFactoryModule_ProvideFactoryFactory;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.work.ListenableWorker;
import androidx.work.WorkerParameters;
import com.college.notetask.data.local.dao.NoteDao;
import com.college.notetask.data.local.dao.TaskDao;
import com.college.notetask.data.local.database.AppDatabase;
import com.college.notetask.data.repository.NoteRepositoryImpl;
import com.college.notetask.data.repository.TaskRepositoryImpl;
import com.college.notetask.di.DatabaseModule_ProvideAppDatabaseFactory;
import com.college.notetask.di.DatabaseModule_ProvideDataStoreFactory;
import com.college.notetask.di.DatabaseModule_ProvideNoteDaoFactory;
import com.college.notetask.di.DatabaseModule_ProvideTaskDaoFactory;
import com.college.notetask.domain.usecase.DeleteNoteUseCase;
import com.college.notetask.domain.usecase.DeleteTaskUseCase;
import com.college.notetask.domain.usecase.GetAllTasksUseCase;
import com.college.notetask.domain.usecase.GetNoteByIdUseCase;
import com.college.notetask.domain.usecase.GetTaskByIdUseCase;
import com.college.notetask.domain.usecase.SaveNoteUseCase;
import com.college.notetask.domain.usecase.SaveTaskUseCase;
import com.college.notetask.domain.usecase.SearchNotesUseCase;
import com.college.notetask.domain.usecase.TogglePinNoteUseCase;
import com.college.notetask.domain.usecase.ToggleTaskStatusUseCase;
import com.college.notetask.utils.BootReceiver;
import com.college.notetask.utils.BootReceiver_MembersInjector;
import com.college.notetask.utils.ReminderScheduler;
import com.college.notetask.utils.ReminderWorker;
import com.college.notetask.utils.ReminderWorker_AssistedFactory;
import com.college.notetask.viewmodel.NotesViewModel;
import com.college.notetask.viewmodel.NotesViewModel_HiltModules;
import com.college.notetask.viewmodel.TaskViewModel;
import com.college.notetask.viewmodel.TaskViewModel_HiltModules;
import com.college.notetask.viewmodel.ThemeViewModel;
import com.college.notetask.viewmodel.ThemeViewModel_HiltModules;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.IdentifierNameString;
import dagger.internal.KeepFieldType;
import dagger.internal.LazyClassKeyMap;
import dagger.internal.MapBuilder;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.SingleCheck;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

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
public final class DaggerNoteTaskApplication_HiltComponents_SingletonC {
  private DaggerNoteTaskApplication_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public NoteTaskApplication_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements NoteTaskApplication_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public NoteTaskApplication_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements NoteTaskApplication_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public NoteTaskApplication_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements NoteTaskApplication_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public NoteTaskApplication_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements NoteTaskApplication_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public NoteTaskApplication_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements NoteTaskApplication_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public NoteTaskApplication_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements NoteTaskApplication_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public NoteTaskApplication_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements NoteTaskApplication_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public NoteTaskApplication_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends NoteTaskApplication_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends NoteTaskApplication_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends NoteTaskApplication_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends NoteTaskApplication_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity mainActivity) {
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Map<Class<?>, Boolean> getViewModelKeys() {
      return LazyClassKeyMap.<Boolean>of(MapBuilder.<String, Boolean>newMapBuilder(3).put(LazyClassKeyProvider.com_college_notetask_viewmodel_NotesViewModel, NotesViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_college_notetask_viewmodel_TaskViewModel, TaskViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_college_notetask_viewmodel_ThemeViewModel, ThemeViewModel_HiltModules.KeyModule.provide()).build());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_college_notetask_viewmodel_ThemeViewModel = "com.college.notetask.viewmodel.ThemeViewModel";

      static String com_college_notetask_viewmodel_TaskViewModel = "com.college.notetask.viewmodel.TaskViewModel";

      static String com_college_notetask_viewmodel_NotesViewModel = "com.college.notetask.viewmodel.NotesViewModel";

      @KeepFieldType
      ThemeViewModel com_college_notetask_viewmodel_ThemeViewModel2;

      @KeepFieldType
      TaskViewModel com_college_notetask_viewmodel_TaskViewModel2;

      @KeepFieldType
      NotesViewModel com_college_notetask_viewmodel_NotesViewModel2;
    }
  }

  private static final class ViewModelCImpl extends NoteTaskApplication_HiltComponents.ViewModelC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<NotesViewModel> notesViewModelProvider;

    private Provider<TaskViewModel> taskViewModelProvider;

    private Provider<ThemeViewModel> themeViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;

      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    private SearchNotesUseCase searchNotesUseCase() {
      return new SearchNotesUseCase(singletonCImpl.noteRepositoryImplProvider.get());
    }

    private GetNoteByIdUseCase getNoteByIdUseCase() {
      return new GetNoteByIdUseCase(singletonCImpl.noteRepositoryImplProvider.get());
    }

    private SaveNoteUseCase saveNoteUseCase() {
      return new SaveNoteUseCase(singletonCImpl.noteRepositoryImplProvider.get());
    }

    private DeleteNoteUseCase deleteNoteUseCase() {
      return new DeleteNoteUseCase(singletonCImpl.noteRepositoryImplProvider.get());
    }

    private TogglePinNoteUseCase togglePinNoteUseCase() {
      return new TogglePinNoteUseCase(singletonCImpl.noteRepositoryImplProvider.get());
    }

    private GetAllTasksUseCase getAllTasksUseCase() {
      return new GetAllTasksUseCase(singletonCImpl.taskRepositoryImplProvider.get());
    }

    private GetTaskByIdUseCase getTaskByIdUseCase() {
      return new GetTaskByIdUseCase(singletonCImpl.taskRepositoryImplProvider.get());
    }

    private SaveTaskUseCase saveTaskUseCase() {
      return new SaveTaskUseCase(singletonCImpl.taskRepositoryImplProvider.get());
    }

    private DeleteTaskUseCase deleteTaskUseCase() {
      return new DeleteTaskUseCase(singletonCImpl.taskRepositoryImplProvider.get());
    }

    private ToggleTaskStatusUseCase toggleTaskStatusUseCase() {
      return new ToggleTaskStatusUseCase(singletonCImpl.taskRepositoryImplProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.notesViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.taskViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.themeViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
    }

    @Override
    public Map<Class<?>, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return LazyClassKeyMap.<javax.inject.Provider<ViewModel>>of(MapBuilder.<String, javax.inject.Provider<ViewModel>>newMapBuilder(3).put(LazyClassKeyProvider.com_college_notetask_viewmodel_NotesViewModel, ((Provider) notesViewModelProvider)).put(LazyClassKeyProvider.com_college_notetask_viewmodel_TaskViewModel, ((Provider) taskViewModelProvider)).put(LazyClassKeyProvider.com_college_notetask_viewmodel_ThemeViewModel, ((Provider) themeViewModelProvider)).build());
    }

    @Override
    public Map<Class<?>, Object> getHiltViewModelAssistedMap() {
      return Collections.<Class<?>, Object>emptyMap();
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_college_notetask_viewmodel_ThemeViewModel = "com.college.notetask.viewmodel.ThemeViewModel";

      static String com_college_notetask_viewmodel_TaskViewModel = "com.college.notetask.viewmodel.TaskViewModel";

      static String com_college_notetask_viewmodel_NotesViewModel = "com.college.notetask.viewmodel.NotesViewModel";

      @KeepFieldType
      ThemeViewModel com_college_notetask_viewmodel_ThemeViewModel2;

      @KeepFieldType
      TaskViewModel com_college_notetask_viewmodel_TaskViewModel2;

      @KeepFieldType
      NotesViewModel com_college_notetask_viewmodel_NotesViewModel2;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.college.notetask.viewmodel.NotesViewModel 
          return (T) new NotesViewModel(viewModelCImpl.searchNotesUseCase(), viewModelCImpl.getNoteByIdUseCase(), viewModelCImpl.saveNoteUseCase(), viewModelCImpl.deleteNoteUseCase(), viewModelCImpl.togglePinNoteUseCase());

          case 1: // com.college.notetask.viewmodel.TaskViewModel 
          return (T) new TaskViewModel(viewModelCImpl.getAllTasksUseCase(), viewModelCImpl.getTaskByIdUseCase(), viewModelCImpl.saveTaskUseCase(), viewModelCImpl.deleteTaskUseCase(), viewModelCImpl.toggleTaskStatusUseCase(), singletonCImpl.reminderSchedulerProvider.get());

          case 2: // com.college.notetask.viewmodel.ThemeViewModel 
          return (T) new ThemeViewModel(singletonCImpl.provideDataStoreProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends NoteTaskApplication_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends NoteTaskApplication_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }
  }

  private static final class SingletonCImpl extends NoteTaskApplication_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<ReminderWorker_AssistedFactory> reminderWorker_AssistedFactoryProvider;

    private Provider<AppDatabase> provideAppDatabaseProvider;

    private Provider<TaskDao> provideTaskDaoProvider;

    private Provider<TaskRepositoryImpl> taskRepositoryImplProvider;

    private Provider<ReminderScheduler> reminderSchedulerProvider;

    private Provider<NoteDao> provideNoteDaoProvider;

    private Provider<NoteRepositoryImpl> noteRepositoryImplProvider;

    private Provider<DataStore<Preferences>> provideDataStoreProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    private Map<String, javax.inject.Provider<WorkerAssistedFactory<? extends ListenableWorker>>> mapOfStringAndProviderOfWorkerAssistedFactoryOf(
        ) {
      return Collections.<String, javax.inject.Provider<WorkerAssistedFactory<? extends ListenableWorker>>>singletonMap("com.college.notetask.utils.ReminderWorker", ((Provider) reminderWorker_AssistedFactoryProvider));
    }

    private HiltWorkerFactory hiltWorkerFactory() {
      return WorkerFactoryModule_ProvideFactoryFactory.provideFactory(mapOfStringAndProviderOfWorkerAssistedFactoryOf());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.reminderWorker_AssistedFactoryProvider = SingleCheck.provider(new SwitchingProvider<ReminderWorker_AssistedFactory>(singletonCImpl, 0));
      this.provideAppDatabaseProvider = DoubleCheck.provider(new SwitchingProvider<AppDatabase>(singletonCImpl, 3));
      this.provideTaskDaoProvider = DoubleCheck.provider(new SwitchingProvider<TaskDao>(singletonCImpl, 2));
      this.taskRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<TaskRepositoryImpl>(singletonCImpl, 1));
      this.reminderSchedulerProvider = DoubleCheck.provider(new SwitchingProvider<ReminderScheduler>(singletonCImpl, 4));
      this.provideNoteDaoProvider = DoubleCheck.provider(new SwitchingProvider<NoteDao>(singletonCImpl, 6));
      this.noteRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<NoteRepositoryImpl>(singletonCImpl, 5));
      this.provideDataStoreProvider = DoubleCheck.provider(new SwitchingProvider<DataStore<Preferences>>(singletonCImpl, 7));
    }

    @Override
    public void injectNoteTaskApplication(NoteTaskApplication noteTaskApplication) {
      injectNoteTaskApplication2(noteTaskApplication);
    }

    @Override
    public void injectBootReceiver(BootReceiver bootReceiver) {
      injectBootReceiver2(bootReceiver);
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return Collections.<Boolean>emptySet();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private NoteTaskApplication injectNoteTaskApplication2(NoteTaskApplication instance) {
      NoteTaskApplication_MembersInjector.injectWorkerFactory(instance, hiltWorkerFactory());
      return instance;
    }

    private BootReceiver injectBootReceiver2(BootReceiver instance) {
      BootReceiver_MembersInjector.injectTaskRepository(instance, taskRepositoryImplProvider.get());
      BootReceiver_MembersInjector.injectReminderScheduler(instance, reminderSchedulerProvider.get());
      return instance;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.college.notetask.utils.ReminderWorker_AssistedFactory 
          return (T) new ReminderWorker_AssistedFactory() {
            @Override
            public ReminderWorker create(Context context, WorkerParameters params) {
              return new ReminderWorker(context, params);
            }
          };

          case 1: // com.college.notetask.data.repository.TaskRepositoryImpl 
          return (T) new TaskRepositoryImpl(singletonCImpl.provideTaskDaoProvider.get());

          case 2: // com.college.notetask.data.local.dao.TaskDao 
          return (T) DatabaseModule_ProvideTaskDaoFactory.provideTaskDao(singletonCImpl.provideAppDatabaseProvider.get());

          case 3: // com.college.notetask.data.local.database.AppDatabase 
          return (T) DatabaseModule_ProvideAppDatabaseFactory.provideAppDatabase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 4: // com.college.notetask.utils.ReminderScheduler 
          return (T) new ReminderScheduler(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 5: // com.college.notetask.data.repository.NoteRepositoryImpl 
          return (T) new NoteRepositoryImpl(singletonCImpl.provideNoteDaoProvider.get());

          case 6: // com.college.notetask.data.local.dao.NoteDao 
          return (T) DatabaseModule_ProvideNoteDaoFactory.provideNoteDao(singletonCImpl.provideAppDatabaseProvider.get());

          case 7: // androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences> 
          return (T) DatabaseModule_ProvideDataStoreFactory.provideDataStore(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          default: throw new AssertionError(id);
        }
      }
    }
  }
}

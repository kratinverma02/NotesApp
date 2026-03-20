package com.college.notetask.utils;

import android.content.Context;
import androidx.work.WorkerParameters;
import dagger.internal.DaggerGenerated;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class ReminderWorker_Factory {
  public ReminderWorker_Factory() {
  }

  public ReminderWorker get(Context context, WorkerParameters params) {
    return newInstance(context, params);
  }

  public static ReminderWorker_Factory create() {
    return new ReminderWorker_Factory();
  }

  public static ReminderWorker newInstance(Context context, WorkerParameters params) {
    return new ReminderWorker(context, params);
  }
}

package com.example.tbcworks.presentation.screens.worker

import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkInfo
import com.example.tbcworks.data.CleanUpWorker
import com.example.tbcworks.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkerViewModel @Inject constructor(
    private val workManager: WorkManager
) : BaseViewModel<WorkerState, WorkerSideEffect, Unit>(WorkerState.Idle) {

    fun startCleanup() {
        setState { WorkerState.Running }

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .build()

        val cleanupRequest = OneTimeWorkRequestBuilder<CleanUpWorker>()
            .setConstraints(constraints)
            .build()

        //.setBackoffCriteria(
        //        BackoffPolicy.EXPONENTIAL,
        //        30_000,
        //        TimeUnit.MILLISECONDS
        //    )

        workManager.enqueue(cleanupRequest)

        viewModelScope.launch {
            workManager.getWorkInfoByIdFlow(cleanupRequest.id)
                .collectLatest { workInfo ->
                    when (workInfo?.state) {
                        WorkInfo.State.SUCCEEDED -> {
                            setState { WorkerState.Success }
                            sendSideEffect(WorkerSideEffect.ShowSnackBar("Cache cleaned!"))
                        }
                        WorkInfo.State.FAILED -> {
                            setState { WorkerState.Failed }
                            sendSideEffect(WorkerSideEffect.ShowSnackBar("Cleanup failed!"))
                        }
                        WorkInfo.State.RUNNING -> setState { WorkerState.Running }
                        else -> {}
                    }
                }
        }
    }
}


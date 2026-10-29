package app.graphe.cartoes.reminder

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import app.graphe.cartoes.domain.nextReminderDelay
import java.time.ZonedDateTime
import java.util.concurrent.TimeUnit

const val REMINDER_WORK = "streak-reminder"
const val REMINDER_MINUTES = 20 * 60

fun scheduleStreakReminder(context: Context, now: ZonedDateTime = ZonedDateTime.now()) {
    val delay = nextReminderDelay(now, REMINDER_MINUTES)
    val request = OneTimeWorkRequestBuilder<StreakReminderWorker>()
        .setInitialDelay(delay.toMillis(), TimeUnit.MILLISECONDS)
        .build()
    WorkManager.getInstance(context).enqueueUniqueWork(
        REMINDER_WORK,
        ExistingWorkPolicy.REPLACE,
        request,
    )
}

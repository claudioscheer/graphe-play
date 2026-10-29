package app.graphe.cartoes.reminder

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import app.graphe.cartoes.CartoesApp
import app.graphe.cartoes.MainActivity
import app.graphe.cartoes.R
import app.graphe.cartoes.domain.displayedStreak
import app.graphe.cartoes.domain.studiedToday
import java.time.LocalDate
import java.time.ZoneId

class StreakReminderWorker(
    context: Context,
    params: WorkerParameters,
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val app = applicationContext as CartoesApp
        val loaded = app.repository.load()
        val today = LocalDate.now(ZoneId.systemDefault())
        if (!studiedToday(loaded.streak.lastStudyDate, today) && canNotify(applicationContext)) {
            val streak = displayedStreak(loaded.streak, today)
            notify(applicationContext, streak)
        }
        scheduleStreakReminder(applicationContext)
        return Result.success()
    }

    private fun notify(context: Context, streak: Int) {
        ensureChannel(context)
        val open = Intent(context, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val pending = PendingIntent.getActivity(
            context,
            0,
            open,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
        val text = if (streak >= 1) {
            "Hoje é um bom dia para responder algumas perguntas. Sua sequência: $streak dias."
        } else {
            "Comece sua sequência hoje."
        }
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_stat_card)
            .setContentTitle("Sua sequência")
            .setContentText(text)
            .setStyle(NotificationCompat.BigTextStyle().bigText(text))
            .setContentIntent(pending)
            .setAutoCancel(true)
            .build()
        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)
    }

    private fun ensureChannel(context: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return
        }
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Sequência",
            NotificationManager.IMPORTANCE_DEFAULT,
        )
        channel.description = "Lembrete diário para manter a sequência."
        val manager = context.getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    private fun canNotify(context: Context): Boolean {
        if (!NotificationManagerCompat.from(context).areNotificationsEnabled()) {
            return false
        }
        if (Build.VERSION.SDK_INT < 33) {
            return true
        }
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS,
        ) == PackageManager.PERMISSION_GRANTED
    }

    private companion object {
        const val CHANNEL_ID = "streak"
        const val NOTIFICATION_ID = 1001
    }
}

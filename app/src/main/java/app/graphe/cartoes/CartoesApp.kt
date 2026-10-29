package app.graphe.cartoes

import android.app.Application
import androidx.work.WorkManager
import app.graphe.cartoes.data.AcfBible
import app.graphe.cartoes.data.CartoesDatabase
import app.graphe.cartoes.data.PracticeRepository
import app.graphe.cartoes.data.RoomStudyRepository
import app.graphe.cartoes.data.StudyRepository
import app.graphe.cartoes.reminder.REMINDER_WORK

class CartoesApp : Application() {
    lateinit var repository: StudyRepository
        private set

    lateinit var practice: PracticeRepository
        private set

    override fun onCreate() {
        super.onCreate()
        val database = CartoesDatabase.create(this)
        repository = RoomStudyRepository(database, assets)
        practice = PracticeRepository(database.practiceDao(), database.dao(), AcfBible(this))
        WorkManager.getInstance(this).cancelUniqueWork(REMINDER_WORK)
    }
}

package com.miu.mdp.quiz

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.miu.mdp.quiz.datasource.QuizDB
import com.miu.mdp.quiz.datasource.repository.QuestionsRepoImpl
import com.miu.mdp.quiz.datasource.repository.UserRepoImpl
import com.miu.mdp.quiz.ui.view_model.MainViewModel
import com.miu.mdp.quiz.ui.view_model.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var appConfiguration: AppBarConfiguration

    private val quizDB: QuizDB by lazy {
        QuizDB(this)
    }

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel = ViewModelProvider(
            this, MainViewModelFactory(
                UserRepoImpl(quizDB.getUserDao()),
                QuestionsRepoImpl(
                    quizDB.getQuestionDao(),
                    quizDB.getResultDao(),
                    quizDB.getQuestionAnswerHistoryDao()
                ),
                application as QuizApplication
            )
        ).get(MainViewModel::class.java)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        appConfiguration = AppBarConfiguration(setOf(R.id.loginFragment, R.id.homeFragment))
        setupActionBarWithNavController(navController, appConfiguration)

    }

    fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return false
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentContainerView)
        return navController.navigateUp(appConfiguration) || super.onSupportNavigateUp()
    }

}

package com.example.meetin

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.meetin.daos.PostDao
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_create_post.*

class CreatePostActivity : AppCompatActivity() {

    private lateinit var postDao: PostDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)

        postDao = PostDao()
        setUpToolbar()

        postButton.setOnClickListener {
            val input = postInput.text.toString()
            if(input.isNotEmpty()){
                postDao.addPost(input)
                finish()
            }
        }
    }
    private fun setUpToolbar() {
        setSupportActionBar(findViewById(R.id.mainToolbar))
        supportActionBar?.title = "MeetIn"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if(id == R.id.logout){
            AlertDialog.Builder(this)
                .setMessage("Do you sure you want to Log-out")
                .setCancelable(false)
                .setPositiveButton("Yes", DialogInterface.OnClickListener(){ dialogInterface: DialogInterface, i: Int ->
                    Firebase.auth.signOut()
                    val signInActivityIntent = Intent(this, SignInActivity::class.java)
                    startActivity(signInActivityIntent)
                    finish()
                })
                .setNegativeButton("No", null)
                .show()
        }
        return super.onOptionsItemSelected(item)
    }
}
package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.databinding.ActivitySignupBinding
import com.example.myapplication.util.UiUtil
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupBtn.setOnClickListener {
            login()
        }

        binding.signupText.setOnClickListener {
            startActivity(Intent(this,SignupActivity::class.java))
            finish()
        }

    }

    fun setInProgress(inProgress : Boolean){
        if(inProgress){
            binding.sProgressBar.visibility = View.VISIBLE
            binding.signupBtn.visibility = View.GONE
        }
        else{
            binding.sProgressBar.visibility = View.GONE
            binding.signupBtn.visibility = View.VISIBLE
        }
    }

    fun login(){
        val email = binding.emailInput.text.toString()
        val password = binding.passwordInput.text.toString()


        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emailInput.setError("Email not Valid")
            return;
        }
        if(password.length<6){
            binding.passwordInput.setError("Minimum 6 characters")
            return;
        }

        loginWithFirebase(email, password)
    }

    fun loginWithFirebase(email : String, password : String){
        setInProgress(true)
        FirebaseAuth.getInstance().signInWithEmailAndPassword(
            email, password
        ).addOnSuccessListener{
            UiUtil.showToast(this, "Login Successfully")
            setInProgress(false)
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }.addOnFailureListener {
            UiUtil.showToast(applicationContext,it.localizedMessage?: "Something went wrong")
            setInProgress(false)
        }

    }
}
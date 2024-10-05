package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivitySignupBinding
import com.example.myapplication.model.UserModel
import com.example.myapplication.util.UiUtil
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class SignupActivity : AppCompatActivity() {

    lateinit var binding : ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupBtn.setOnClickListener {
            signup()
        }

        binding.loginText.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }

        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
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

    fun signup(){
        val username = binding.usernameInput.text.toString()
        val email = binding.emailInput.text.toString()
        val password = binding.passwordInput.text.toString()
        val confirmPassword = binding.confirmPasswordInput.text.toString()

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emailInput.setError("Email not Valid")
            return;
        }
        if(password.length<6){
            binding.passwordInput.setError("Minimum 6 characters")
            return;
        }
        if(password!=confirmPassword){
            binding.confirmPasswordInput.setError("Password not matched")
            return;
        }
        signupWithFirebase(username, password, email)
    }

    fun signupWithFirebase(username : String, password : String, email : String){
        setInProgress(true)
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(
            email, password
        ).addOnSuccessListener {
            it.user?.let {user->
                val userModel = UserModel(user.uid,username, email )
                Firebase.firestore.collection("users")
                    .document(user.uid)
                    .set(userModel).addOnSuccessListener {
                        UiUtil.showToast(applicationContext, "Account created successfully")
                        setInProgress(false)
                        startActivity(Intent(this,HomeActivity::class.java))
                        finish()
                    }
            }
        }.addOnFailureListener {
            UiUtil.showToast(applicationContext,it.localizedMessage?: "Something went wrong")
            setInProgress(false)
        }

    }
}
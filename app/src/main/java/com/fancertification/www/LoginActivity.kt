package com.fancertification.www

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.fancertification.www.databinding.ActivityLoginBinding
import com.google.common.base.Strings.isNullOrEmpty
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var auth:FirebaseAuth

    private var gender=""
    private var checkEmail=0
    private var checkEmailLogin=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init() //initblock
        initEditText() //초기화면 구성을 위한 함수
        initRegister() // 회원가입 세팅

    }

    private fun init(){
        auth= FirebaseAuth.getInstance()
        binding.apply {

            if(auth.currentUser!=null){ //로그인 된 상태면 바로 메인으로 이동
                val intent= Intent(this@LoginActivity,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            LoginBtn.setOnClickListener { //로그인 버튼
                val intent= Intent(this@LoginActivity,
                    MainActivity::class.java)
                //startActivity(intent)
                //finish()
                login()
            }

            // 회원가입/취소 버튼을 누름에 따라 컴포넌트들의 View를 바꾸도록 설정
            signupBtn.setOnClickListener { //회원가입 하기
                loginLayout.visibility=View.GONE
                signupPopup.visibility=View.VISIBLE
            }
            cancleBtn.setOnClickListener { //회원가입 취소
                loginLayout.visibility=View.VISIBLE
                signupPopup.visibility=View.GONE
                initSignup()
            }
            okBtn.setOnClickListener{ // 회원가입 신청
                register()
            }
        } // binding
    } //init


    private fun initSignup(){ // 한 액티비티에서 회원가입 화면을 처리해주기 위해서 다음과 같이
        //컴포넌트의 View속성을 조정
        binding.apply {
            loginLayout.visibility=View.VISIBLE
            emailSignupText.text?.clear()
            pwSignupText.text?.clear()
            nameSignupText.text?.clear()
            checkMale.isChecked=false
            checkFemale.isChecked=false
            signupInputLayout.error=null
            signupInputLayout2.error=null
            signupInputLayout3.error=null
            checkFemale.error=null
            signupPopup.visibility=View.GONE
        } //binding
    } //initSignup

    
    private fun login(){
        binding.apply {
            val email=emailText.text.toString()
            val pw=pwText.text.toString()
            if(checkEmailLogin==0){
                return
            }
            if(pw.isEmpty()){
                loginEmailInputLayout.setError("비밀번호가 필요합니다.")
                loginEmailInputLayout.requestFocus()
                return
            }else{
                loginEmailInputLayout.error=null
            }

            // 파이어베이스와 연동하는 시간동안 ProgressBar를 설정
            progressBar.visibility=View.VISIBLE
            auth.signInWithEmailAndPassword(email,pw).addOnCompleteListener {
                if(it.isSuccessful){
                    // 메인으로 이동
                    val intent= Intent(this@LoginActivity,
                        MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this@LoginActivity,"로그인 실패",Toast.LENGTH_SHORT).show()
                }
                progressBar.visibility=View.GONE
            }
        } //binding
    } // login


    private fun initEditText(){
        binding.apply {
            emailText.addTextChangedListener {
                if(!isNullOrEmpty(it.toString()) && Patterns.EMAIL_ADDRESS.matcher(it.toString()).matches()){
                    loginEmailInputLayout.error=null
                    checkEmailLogin=1
                }else{
                    loginEmailInputLayout.error="이메일 형식이 올바르지 않습니다."
                    checkEmailLogin=0
                }
            } // 이메일 Inputtext(로그인)
        } //binding
    } // initedittext

    private fun initRegister(){
        //회원가입 시작
        binding.apply {
            checkMale.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked){ gender="남" }
                else{ gender="" }
                checkFemale.isChecked=false
            }
            checkFemale.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked){ gender="여" }
                else{ gender="" }

                checkMale.isChecked=false
            } // 체크박스를 통한 남녀 속성 이후 회의를 거쳐 다른 속성 혹은 삭제도 가능

            emailSignupText.addTextChangedListener {
                if(!isNullOrEmpty(it.toString()) && Patterns.EMAIL_ADDRESS.matcher(it.toString()).matches()){
                    signupInputLayout.error=null
                    checkEmail=1
                }else{
                    signupInputLayout.error="이메일 형식이 올바르지 않습니다."
                    checkEmail=0
                }
            } // 이메일 Inputtext(회원가입)

        } //binding
    } //initRegister

    private fun register(){
        // 회원가입 함수
        binding.apply {
            val email=emailSignupText.text.toString().trim()
            val pw=pwSignupText.text.toString()
            val nickname=nameSignupText.text.toString().trim()

            if(checkEmail==0){
                return
            }
            
            // 회원가입시 필요한 조건들 따로 주석을 달지 않아도 setError구문을 통해서 확인
            if(email.isEmpty()){
                signupInputLayout.setError("이메일이 필요합니다.")
                signupInputLayout.requestFocus()
                return
            }

            if(pw.isEmpty()){
                signupInputLayout2.setError("비밀번호가 필요합니다.")
                signupInputLayout2.requestFocus()
                return
            }else{ signupInputLayout2.error=null }

            if(pw.length<6){
                signupInputLayout2.setError("비밀번호는 6자 이상이어야 합니다.")
                signupInputLayout2.requestFocus()
                return
            }else{ signupInputLayout2.error=null }

            if(nickname.length<2){
                signupInputLayout3.setError("닉네임은 2자 이상이어야 합니다.")
                signupInputLayout3.requestFocus()
                return
            }else{ signupInputLayout3.error=null }

            if(nickname.length>10){
                signupInputLayout3.setError("닉네임은 10자 이하여야 합니다.")
                signupInputLayout3.requestFocus()
                return
            }else{ signupInputLayout3.error=null }

            if(gender==""){
                checkFemale.setError("성별을 선택하세요.")
                checkFemale.requestFocus()
                return
            }else{ checkFemale.error=null }
            
            // 파이어베이스와 연동시 잠깐의 공백을 표현하기 위한 progressbar 및 addonCompleteListner를 통한 작업
            progressBar.visibility=View.VISIBLE
            auth.createUserWithEmailAndPassword(email,pw)
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        // 나중에 파이어스토어에 데이터를 넣어야 한다면 해당 과정에서 해주면 될듯 싶음
                    }else{
                        Toast.makeText(this@LoginActivity,"이미 등록된 계정입니다.",Toast.LENGTH_SHORT).show()
                        // 파이어베이스 중복 검사
                    }
                }
            progressBar.visibility=View.GONE
        }
    } // register

//test
}
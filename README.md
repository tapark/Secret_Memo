# 보안 메모장

### AlertDialog 생성 (확인/취소 팝업창)
~~~kotlin
AlertDialog.Builder(this)
    .setTitle("Password Fail")
    .setMessage("비밀번호 오류")
    .setPositiveButton("확인") {_, _ -> }
//	.setNegativeButton("취소") {_, _ -> }
    .create()
    .show()
// setPositiveButton의 람다식 안에 버튼 클릭 시 수행할 동작을 정의 할 수있음
// Parameter는 미사용으로 _, _ 처리
// TODO Parameter 사용법? 사용하는 경우?
~~~

### getSharedPreferences : Local File에 간단한 Data 저장
~~~kotlin
// "pass" 라는 키(key)를 가진 MODE_PRIVATE(다른 앱과 공유X) 파일생성
val passwordSet = getSharedPreferences("pass", Context.MODE_PRIVATE)
// 파일의 위치 : data/data/(package_name)/shared_prefs/SharedPreference
// "pass" 키(key)에 값(value) 저장
passwordSet.edit {
    putString("pass", passwordFromUser)
    commit()
}
// "pass" 키(key)에 저장된 String 값(value) 가져오기
passwordSet.getString("pass", "0000") // "0000"는 값이 없을때 호출됨
~~~

### textView.text = string  vs  textView.setText(string)
내부적인 동작방식은 차이없음, 전자는 kotlin style 후자는 java style

### Handler 와 Runnable
~~~kotlin
// MainThread(UI) 로 보낼 수 있는 handler 생성
private val handler = Handler(Looper.getMainLooper())
// Handler를 통해 실행될 동작을 Runnable 로 정의
val runnable = Runnable {
    getSharedPreferences("memo", Context.MODE_PRIVATE).edit {
        putString("memo", memoEditText.text.toString())
		commit()
    } // getSharedPreferences에 String 저장
}
// EditText 가 변경될때 실행
memoEditText.addTextChangedListener {
    // 5000ms 전에 EditText 가 변경되었다면 runnable 실행 취소 (저장X)
	handler.removeCallbacks(runnable)
	// 5000ms delay후 runnable 실행 (String 저장)
    handler.postDelayed(runnable, 5000)
}
~~~

### editText.addTextChangedListener : EditText의 상태를 감지
~~~kotlin
//일반적인 함수 정의
editText.addTextChangedListener(object: TextWatcher {
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { "주로 텍스트가 변화 되었을때 처리" }

	override fun afterTextChanged(p0: Editable?) { }
    })
~~~

### Activity.kt에서 AppCompatButton의 배경색 변경
~~~kotlin
changePasswordButton.setBackgroundColor(Color.RED)
//Color.RED 대신 getColor(R.color.red) 사용가능?
~~~

### main.xml에서 text font 변경
~~~kotlin
android:fontFamily="@font/naver_pen"
~~~

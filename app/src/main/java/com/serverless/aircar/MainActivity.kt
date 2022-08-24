package com.serverless.aircar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior

class MainActivity : AppCompatActivity() {

    lateinit var coordinatorLayout: CoordinatorLayout
    var persistenetBottomSheet: BottomSheetBehavior<View>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        coordinatorLayout = findViewById(R.id.coordinator)


        val btn = findViewById<Button>(R.id.main_button)

        initPersistentBottomSheet()
    }

    private fun initPersistentBottomSheet() {
        // persistent bottom sheet로 사용할 view 획득
        // coordinatorLayout안에 설정되어 있으므로 그곳에서 findViewById를 사용하여 얻어온다
        val bottomSheet = coordinatorLayout.findViewById<View>(R.id.bottom_sheet)
        // 위에서 획득한 view를 BottomSheet로 지정
        persistenetBottomSheet = BottomSheetBehavior.from(bottomSheet)

    }

    val bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
        // bottom sheet의 상태값 변경
        override fun onStateChanged(bottomSheet: View, newState: Int) {

        }

        // botton sheet가 스크롤될 때 호출
        override fun onSlide(bottomSheet: View, slideOffset: Float) {

        }
    }

}
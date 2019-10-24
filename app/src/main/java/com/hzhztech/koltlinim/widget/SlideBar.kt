package com.hzhztech.koltlinim.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.hzhztech.koltlinim.R
import org.jetbrains.anko.sp

class SlideBar(context: Context?, attrs: AttributeSet? = null) : View(context, attrs) {

    var sectionHeight = 0f

    var paint = Paint()

    companion object {
        // 26个字母
        private val SECTIONS = arrayOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#")
    }

    init {
        paint.apply {
            color = resources.getColor(R.color.qq_section_text_color)
            textSize = sp(12).toFloat()  // anko库中转化为12sp
            //对其居中
            textAlign = Paint.Align.CENTER
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        //计算每个字符分配的高度
        sectionHeight = h * 1.0f / SECTIONS.size
    }

    override fun onDraw(canvas: Canvas) {
        var x = width * 1.0f/ 2
        var y = sectionHeight
        SECTIONS.forEach {
            canvas.drawText(it,x,y,paint)
            //更新Y 绘制下一个字母
            y += sectionHeight
        }
    }
}
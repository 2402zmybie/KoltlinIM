package com.hzhztech.koltlinim.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.hzhztech.koltlinim.R
import org.jetbrains.anko.sp

class SlideBar(context: Context?, attrs: AttributeSet? = null) : View(context, attrs) {

    var sectionHeight = 0f
    var textBaseLine = 0f

    var paint = Paint()

    var onSectionChangeListener: OnSectionChangeListener?  = null

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
        val fontMetrics = paint.fontMetrics
        //计算绘制文本的高度
        val textHeight = fontMetrics.descent - fontMetrics.ascent
        //计算基准线
        textBaseLine = sectionHeight /2 + (textHeight / 2 - fontMetrics.descent)

    }

    override fun onDraw(canvas: Canvas) {
        var x = width * 1.0f/ 2
        var baseline = textBaseLine
        SECTIONS.forEach {
            canvas.drawText(it,x,baseline,paint)
            //更新Y 绘制下一个字母
            baseline += sectionHeight
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                setBackgroundResource(R.drawable.bg_slide_bar)
                //找到点击的字母
                val index = getTouchIndex(event)
                val firstLetter = SECTIONS[index]
                onSectionChangeListener?.onSectionChange(firstLetter)
            }
            MotionEvent.ACTION_MOVE -> {
                //找到点击的字母
                val index = getTouchIndex(event)
                val firstLetter = SECTIONS[index]
                onSectionChangeListener?.onSectionChange(firstLetter)
            }
            MotionEvent.ACTION_UP -> {
                setBackgroundColor(Color.TRANSPARENT)
                onSectionChangeListener?.onSlideFinish()
            }
        }
        return true //消费事件
    }

    private fun getTouchIndex(event: MotionEvent): Int {
        var index = (event.y / sectionHeight).toInt()
        //越界检查
        if(index < 0) {
            index = 0
        }else if(index >= SECTIONS.size) {
            index = SECTIONS.size - 1
        }
        return index
    }

    interface OnSectionChangeListener {
        fun onSectionChange(firstLetter:String)
        fun onSlideFinish()  //滑动结束的回调
    }
}
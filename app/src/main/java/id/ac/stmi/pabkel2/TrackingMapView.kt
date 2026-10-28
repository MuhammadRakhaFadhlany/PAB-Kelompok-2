package id.ac.stmi.pabkel2

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class TrackingMapView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val road = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = 0xFFF7F7F7.toInt(); strokeWidth = 14f; style = Paint.Style.STROKE }
    private val routePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = 0xFFEF562B.toInt(); strokeWidth = 5f; style = Paint.Style.STROKE; strokeCap = Paint.Cap.ROUND; strokeJoin = Paint.Join.ROUND }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val save = canvas.save()
        val scaleX = width / 360f
        val scaleY = height / 200f
        canvas.scale(scaleX, scaleY)
        paint.color = 0xFFD8DDDB.toInt()
        canvas.drawRoundRect(RectF(0f, 0f, 360f, 200f), 14f, 14f, paint)
        paint.color = 0xFFA4CF98.toInt()
        canvas.drawRect(38f, 10f, 102f, 85f, paint)
        canvas.drawRect(150f, 80f, 220f, 145f, paint)
        canvas.drawRect(278f, 12f, 332f, 86f, paint)
        canvas.drawRect(22f, 142f, 70f, 190f, paint)
        val roads = listOf(
            floatArrayOf(0f, 45f, 360f, 45f), floatArrayOf(0f, 106f, 360f, 106f),
            floatArrayOf(0f, 164f, 360f, 164f), floatArrayOf(122f, 0f, 122f, 200f),
            floatArrayOf(250f, 0f, 250f, 200f), floatArrayOf(8f, 192f, 140f, 20f),
            floatArrayOf(180f, 15f, 356f, 178f)
        )
        roads.forEach { canvas.drawLine(it[0], it[1], it[2], it[3], road) }
        val route = Path().apply { moveTo(72f, 30f); lineTo(108f, 30f); lineTo(132f, 66f); lineTo(188f, 66f); lineTo(188f, 124f); lineTo(236f, 124f); lineTo(268f, 94f) }
        canvas.drawPath(route, routePaint)
        drawPin(canvas, 72f, 30f)
        drawPin(canvas, 268f, 94f)
        canvas.restoreToCount(save)
    }

    private fun drawPin(canvas: Canvas, x: Float, y: Float) {
        paint.color = 0xFFEF562B.toInt()
        canvas.drawCircle(x, y, 9f, paint)
        paint.color = 0xFFFFFFFF.toInt()
        canvas.drawCircle(x, y, 4f, paint)
    }
}

package com.example.tbcworks

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Patterns
import android.util.TypedValue
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.AppCompatToggleButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tbcworks.databinding.ActivityMainBinding
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        applyParameters()

    }



    private fun applyParameters() {
        binding.main.apply {
            orientation = LinearLayoutCompat.VERTICAL
            setBackgroundResource(R.color.white)
        }

        binding.picFrameLayout.apply {
            layoutParams = LinearLayoutCompat.LayoutParams(374.dp, 460.dp).apply {
                gravity = Gravity.CENTER_HORIZONTAL
                topMargin = 28.dp
            }
            setBackgroundResource(R.drawable.pic)
        }

        binding.backBtn.apply {
            layoutParams = FrameLayout.LayoutParams(44.dp, 44.dp).apply {
                gravity = Gravity.START or Gravity.TOP
                topMargin = 30.dp
                marginStart = 32.dp
            }
            setBackgroundResource(R.drawable.ellipse)
            setImageResource(R.drawable.left)
            scaleType = ImageView.ScaleType.CENTER_INSIDE
            setPadding(8.dp, 8.dp, 8.dp, 8.dp)
            setColorFilter(ContextCompat.getColor(context, R.color.lightGray))
        }

        binding.bookmarkBtn.apply {
            layoutParams = FrameLayout.LayoutParams(44.dp, 44.dp).apply {
                gravity = Gravity.END or Gravity.TOP
                topMargin = 30.dp
                marginEnd = 32.dp
            }
            setBackgroundResource(R.drawable.ellipse)
            setImageResource(R.drawable.baseline_bookmark_border_24)
            setColorFilter(ContextCompat.getColor(context, R.color.lightGray))
            setPadding(3.dp, 3.dp, 3.dp, 3.dp)
        }

        binding.bottomFrameLayout.apply {
            layoutParams = FrameLayout.LayoutParams(331.dp, 104.dp).apply {
                gravity = Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM
                bottomMargin = 20.dp
            }
            setBackgroundResource(R.drawable.frame_bg)
            setPadding(15.sp, 15.sp, 15.sp, 15.sp)
            elevation = 8f
            alpha = 0.8f
        }

        binding.nameTextView.apply {
            text = getString(R.string.andes_mountain)
            textSize = 24f
            setTypeface(typeface, Typeface.BOLD)
            setTextColor(ContextCompat.getColor(context, R.color.white))
        }

        binding.priceTag.apply {
            text = getString(R.string.price)
            textSize = 16f
            setTextColor(ContextCompat.getColor(context, R.color.lightGray))
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.END
                topMargin = 8.dp
            }
        }

        binding.locationLayout.apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.BOTTOM
            }
        }

        binding.locationIcon.apply {
            layoutParams = LinearLayoutCompat.LayoutParams(22.dp, 22.dp)
            setBackgroundResource(R.drawable.baseline_location_on_24)
            backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(context, R.color.lightGray)
            )
        }

        binding.locationTextView.apply {
            layoutParams = LinearLayoutCompat.LayoutParams(122.dp, 22.dp)
            text = getString(R.string.south_america)
            textSize = 16f
            setTextColor(ContextCompat.getColor(context, R.color.lightGray))
        }

        binding.priceLayout.apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.BOTTOM or Gravity.END
            }
        }

        binding.dollarSign.apply {
            text = getString(R.string.dollar)
            textSize = 20f
            setTextColor(ContextCompat.getColor(context, R.color.textBlack))
        }


        binding.priceNumber.apply {
            text = getString(R.string._230)
            textSize = 25f
            setTextColor(ContextCompat.getColor(context, R.color.lightGray))
        }

        binding.headlineLayout.apply {
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = 20.dp
                marginStart = 28.dp
            }
        }
        binding.chosenHeadline.apply {
            text = getString(R.string.overview)
            textSize = 22f
            setTypeface(typeface, Typeface.BOLD)
            setTextColor(ContextCompat.getColor(context, R.color.black))
        }

        binding.secondHeadline.apply {
            text = getString(R.string.details)
            textSize = 16f
            setTextColor(ContextCompat.getColor(context, R.color.gray))
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
            ).apply {
                marginStart = 50.dp
                weight = 1f
            }
        }


        binding.infoLayout.apply {
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(28.dp, 20.dp, 28.dp, 0)
            }
        }


        binding.clockLayout.apply {
            layoutParams = LinearLayoutCompat.LayoutParams(
                0,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
            ).apply {
                weight = 1f
            }
            gravity = Gravity.CENTER_HORIZONTAL
        }

        binding.clockIcon.apply {
            setBackgroundResource(R.drawable.rectangle84)
            setImageResource(R.drawable.clock)
            scaleType = ImageView.ScaleType.CENTER_INSIDE
        }
        binding.clockText.apply {
            text = getString(R.string._8_hours)
            textSize = 18f
            setTextColor(ContextCompat.getColor(context, R.color.gray))
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
            ).apply {
                marginStart = 5.dp
                weight = 1f
            }
        }


        binding.cloudLayout.apply {
            layoutParams = LinearLayoutCompat.LayoutParams(
                0,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
            ).apply {
                weight = 1f
            }
        }

        binding.cloudIcon.apply {
            setBackgroundResource(R.drawable.rectangle84)
            setImageResource(R.drawable.icon_cloud)
            scaleType = ImageView.ScaleType.CENTER_INSIDE
        }

        binding.cloudText.apply {
            text = getString(R.string._16_u00b0c)
            textSize = 18f
            setTextColor(ContextCompat.getColor(context, R.color.gray))
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
            ).apply {
                marginStart = 5.dp
                weight = 1f
            }
        }
        binding.starLayout.apply {
            layoutParams = LinearLayoutCompat.LayoutParams(
                0,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
            ).apply {
                weight = 1f
            }
        }

        binding.starIcon.apply {
            setBackgroundResource(R.drawable.rectangle84)
            setImageResource(R.drawable.vector)
            scaleType = ImageView.ScaleType.CENTER_INSIDE
        }
        binding.starText.apply {
            text = getString(R.string._4_5)
            textSize = 18f
            setTextColor(ContextCompat.getColor(context, R.color.gray))
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
            ).apply {
                marginStart = 5.dp
                weight = 1f
            }
        }


        binding.fade.apply {
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                0
            ).apply {
                weight = 1f
                setMargins(20.dp, 30.dp, 20.dp, 0)
            }
            foreground = ContextCompat.getDrawable(context, R.drawable.text_fade)
            setPadding(0, 0, 0, 20.dp)
        }



        binding.description.apply {
            text = getString(R.string.long_text)
            textSize = 18f
            setTextColor(ContextCompat.getColor(context, R.color.gray))
            setLineSpacing(4.dp.toFloat(), 1f)
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(20.dp, 0, 20.dp, 0)
            }
        }
        binding.bookBtn.apply {
            text = getString(R.string.book_now)
            textSize = 18f
            setTextColor(ContextCompat.getColor(context, R.color.white))
            setBackgroundColor(ContextCompat.getColor(context, R.color.black))
            icon = ContextCompat.getDrawable(context, R.drawable.send)
            iconGravity = MaterialButton.ICON_GRAVITY_TEXT_END
            iconPadding = 8.dp
            gravity = Gravity.CENTER
            cornerRadius = 20.dp
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                66.dp
            ).apply {
                setMargins(50.dp, 20.dp, 50.dp, 20.dp)
            }
        }
    }

    private val Int.dp: Int
        get() = (this * resources.displayMetrics.density).toInt()

    private val Int.sp: Int
        get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            this.toFloat(),
            resources.displayMetrics
        ).toInt()

}
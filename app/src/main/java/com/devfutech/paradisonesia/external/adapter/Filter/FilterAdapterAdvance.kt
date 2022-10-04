package com.devfutech.paradisonesia.external.adapter.Filter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devfutech.paradisonesia.R
import com.devfutech.paradisonesia.domain.model.advance_filter.AdvanceFilter
import com.devfutech.paradisonesia.domain.model.product.Product
import com.devfutech.paradisonesia.external.utils.FileUtils
import com.devfutech.paradisonesia.presentation.bottomsheet.filter.FilterBottomSheet
import com.google.android.material.slider.LabelFormatter
import com.google.android.material.slider.RangeSlider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import timber.log.Timber

class FilterAdapterAdvance(
    private val onItemSelected: (AdvanceFilter) -> Unit,
) : RecyclerView.Adapter<FilterAdapterAdvance.ViewHolder>() {

    var priceMin : Int = 0
    var priceMax : Int = 0


    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.filter_fragment, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var price = emptyList<Int>()
        val tempPrice = FilterBottomSheet.map.get("price")?.removeSurrounding("[", "]")?.replace(" ","")?.split(",")?.map { it.toInt() }

        Timber.tag("KUNTUL").d("price " + tempPrice?.get(0))

        FileUtils.dateMask(holder.tieStartDate, holder.tlStartDate)
        FileUtils.dateMask(holder.tieEndDate, holder.tlEndDate)

        var valueFrom = holder.sbPrice.valueFrom.toInt()
        var valueTo = holder.sbPrice.valueTo.toInt()

        if(tempPrice?.isNotEmpty() == true) {
            //holder.sbPrice.valueFrom = tempPrice.get(0).toFloat()
           // holder.sbPrice.valueTo = tempPrice.get(1).toFloat()
            valueFrom = tempPrice.get(0)
            valueTo =  tempPrice.get(1)
        }

        //holder.sbPrice.valueTo = valueTo.toFloat()
        //holder.sbPrice.valueFrom = valueFrom.toFloat()
        holder.sbPrice.values =  listOf(valueTo.toFloat(),valueFrom.toFloat())
        holder.tiePriceMinim.setText(holder.itemView.resources.getString(R.string.final_price, FileUtils.convertToCurrency(valueFrom)))
        holder.tiePriceMax.setText(holder.itemView.resources.getString(R.string.final_price, FileUtils.convertToCurrency(valueTo)))

        holder.sbPrice.labelBehavior = LabelFormatter.LABEL_GONE

        holder.sbPrice.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener{
            override fun onStartTrackingTouch(slider: RangeSlider) {
                val values = slider.values
                Timber.tag("FilterBottomSheet").d(values[0].toString())
                Timber.tag("FilterBottomSheet").d(values[1].toString())

                var minPrice = holder.itemView.resources.getString(R.string.final_price, FileUtils.convertToCurrency(values[0].toInt()))
                var maxPrice = holder.itemView.resources.getString(R.string.final_price,  FileUtils.convertToCurrency(values[1].toInt()))

                if(values[0]==1.0E7.toFloat()||values[1]==1.0E7.toFloat()){
                    val minPriceConverted = String.format("%.0f", values[0])
                    val maxPriceConverted = String.format("%.0f", values[1])
                    minPrice = holder.itemView.resources.getString(R.string.final_price, FileUtils.convertToCurrency(minPriceConverted.toInt()))
                    maxPrice = holder.itemView.resources.getString(R.string.final_price, FileUtils.convertToCurrency(maxPriceConverted.toInt()))
                }

                holder.tiePriceMinim.setText(minPrice)
                holder.tiePriceMax.setText(maxPrice)
            }

            override fun onStopTrackingTouch(slider: RangeSlider) {
                val values = slider.values
                Timber.tag("FilterBottomSheet").d(values[0].toString())
                Timber.tag("FilterBottomSheet").d(values[1].toString())

                var minPrice = holder.itemView.resources.getString(R.string.final_price, FileUtils.convertToCurrency(values[0].toInt()))
                var maxPrice = holder.itemView.resources.getString(R.string.final_price,  FileUtils.convertToCurrency(values[1].toInt()))

                if(values[0]==1.0E7.toFloat()||values[1]==1.0E7.toFloat()){
                    val minPriceConverted = String.format("%.0f", values[0])
                    val maxPriceConverted = String.format("%.0f", values[1])
                    minPrice = holder.itemView.resources.getString(R.string.final_price, FileUtils.convertToCurrency(minPriceConverted.toInt()))
                    maxPrice = holder.itemView.resources.getString(R.string.final_price, FileUtils.convertToCurrency(maxPriceConverted.toInt()))
                }

                holder.tiePriceMinim.setText(minPrice)
                holder.tiePriceMax.setText(maxPrice)

                price = listOf(values[0].toInt(), values[1].toInt()).take(2)
                onItemSelected(AdvanceFilter(price, 3, price))
            }
        })


        holder.sbPrice.addOnChangeListener(RangeSlider.OnChangeListener { slider, value, fromUser ->

            val values = slider.values
            Timber.tag("FilterBottomSheet").d(values[0].toString())
            Timber.tag("FilterBottomSheet").d(values[1].toString())

            var minPrice = holder.itemView.resources.getString(R.string.final_price, FileUtils.convertToCurrency(values[0].toInt()))
            var maxPrice = holder.itemView.resources.getString(R.string.final_price,  FileUtils.convertToCurrency(values[1].toInt()))

            if(values[0]==1.0E7.toFloat()||values[1]==1.0E7.toFloat()){
                val minPriceConverted = String.format("%.0f", values[0])
                val maxPriceConverted = String.format("%.0f", values[1])
                minPrice = holder.itemView.resources.getString(R.string.final_price, FileUtils.convertToCurrency(minPriceConverted.toInt()))
                maxPrice =holder.itemView.resources.getString(R.string.final_price, FileUtils.convertToCurrency(maxPriceConverted.toInt()))
            }

            holder.tiePriceMinim.setText(minPrice)
            holder.tiePriceMax.setText(maxPrice)
        })


        price = listOf(0, 10000000)
        onItemSelected(AdvanceFilter(price, null, emptyList()))
    }


    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val sbPrice : RangeSlider = itemView.findViewById(R.id.sb_price)
        val rbRating : RatingBar = itemView.findViewById(R.id.rb_rating)
        val tiePriceMinim : TextInputEditText = itemView.findViewById(R.id.tie_price_minim)
        val tiePriceMax : TextInputEditText = itemView.findViewById(R.id.tie_price_max)
        val tieStartDate: TextInputEditText = itemView.findViewById(R.id.tie_start_date)
        val tieEndDate : TextInputEditText = itemView.findViewById(R.id.tie_end_date)
        val tlStartDate: TextInputLayout = itemView.findViewById(R.id.tl_start_date)
        val tlEndDate : TextInputLayout = itemView.findViewById(R.id.tl_end_date)
    }

    override fun getItemCount(): Int {

        return 1
    }
}
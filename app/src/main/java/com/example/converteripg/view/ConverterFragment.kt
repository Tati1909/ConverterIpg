package com.example.converteripg.view

import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.converteripg.App
import com.example.converteripg.R
import com.example.converteripg.data.converter.ConverterFactory
import com.example.converteripg.databinding.ViewConverterBinding
import com.example.converteripg.presentation.converter.ConverterPresenter
import com.example.converteripg.scheduler.SchedulersFactory
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class ConverterFragment : MvpAppCompatFragment(), ConverterView {

    companion object {
        fun newInstance(): Fragment = ConverterFragment()
    }

    private val presenter by moxyPresenter {
        ConverterPresenter(
            converter = ConverterFactory.create(requireContext()),
            schedulers = SchedulersFactory.create()
        )
    }

    private var _binding: ViewConverterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ViewConverterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().title = getString(R.string.converter_tittle)

        binding.button.setOnClickListener { pickImage() }
    }

    //выбираем картинку из галереи
    private fun pickImage() {
        /**
         * ACTION_GET_CONTENT
        Разрешить пользователю выбрать определенный тип данных и вернуть их.
        Обычно вы указываете широкий тип MIME (например, image/ *),
        в результате чего пользователь может выбирать из широкого диапазона
        только картинки.*/
        val intent = Intent(ACTION_GET_CONTENT)
        intent.type = "image/*"

        startActivityForResult(intent, 1)
    }

     //начинаем конвертацию
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        data?.data?.let(presenter::convert)
            ?: Toast.makeText(requireContext(), "Изображение не выбрано", Toast.LENGTH_SHORT).show()
    }

    override fun showContent(uri: Uri?) {

        /**
         * Любое изображение, которое мы загружаем из графического файла,
         * является набором цветных точек (пикселей). А информацию о каждой точке можно сохранить в битах.
         * Отсюда и название - карта битов или по-буржуйски - bitmap.
         */
        val bitmap: Bitmap? =
            uri?.let { MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri) }

        binding.progressBar.visibility = View.VISIBLE
        binding.image.setImageBitmap(bitmap)
    }

    override fun showLoading() {

        binding.progressBar.visibility = View.VISIBLE

        //если хотим отменить загрузку
        binding.button.setOnClickListener {
            presenter.cancel()
        }
        binding.button.text = getString(R.string.cancel)
    }

    override fun showError(error: Throwable) {
        Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
    }

}
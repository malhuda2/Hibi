/*
 * Copyright 2020 Marc Donald
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.marcdonald.hibi.screens.addbookdialog

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.button.MaterialButton
import com.marcdonald.hibi.R
import com.marcdonald.hibi.internal.BOOK_ID_KEY
import com.marcdonald.hibi.internal.base.HibiDialogFragment

class AddBookDialog : HibiDialogFragment() {

	private val viewModel by viewModels<AddBookViewModel> { viewModelFactory }

	// <editor-fold desc="UI Components">
	private lateinit var input: EditText
	private lateinit var dialogTitle: TextView
	// </editor-fold>

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		val view = inflater.inflate(R.layout.dialog_new_book, container, false)
		bindViews(view)
		setupObservers()
		return view
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		arguments?.let {
			viewModel.passArguments(requireArguments().getInt(BOOK_ID_KEY, 0))
		}
	}

	private fun bindViews(view: View) {
		dialogTitle = view.findViewById(R.id.txt_add_book_title)
		input = view.findViewById(R.id.edt_new_book_input)
		val saveButton: MaterialButton = view.findViewById(R.id.btn_save_book)
		saveButton.setOnClickListener(saveClickListener)
		val deleteButton: MaterialButton = view.findViewById(R.id.btn_delete_book)
		deleteButton.setOnClickListener(deleteClickListener)
		input.setOnKeyListener(saveOnEnterListener)
		input.requestFocus()
	}

	private fun setupObservers() {
		viewModel.bookTitle.observe(viewLifecycleOwner, Observer { value ->
			value?.let { title ->
				input.setText(title)
			}
		})

		viewModel.isEditMode.observe(viewLifecycleOwner, Observer { value ->
			value?.let { isEditMode ->
				if(isEditMode)
					dialogTitle.text = resources.getString(R.string.edit_book)
			}
		})

		viewModel.displayEmptyContentWarning.observe(viewLifecycleOwner, Observer { value ->
			value?.let { show ->
				if(show)
					input.error = resources.getString(R.string.empty_content_warning)
			}
		})

		viewModel.displayDuplicateNameWarning.observe(viewLifecycleOwner, Observer { value ->
			value?.let { show ->
				if(show)
					input.error = resources.getString(R.string.book_already_exists)
			}
		})

		viewModel.dismiss.observe(viewLifecycleOwner, Observer { value ->
			value?.let { dismiss ->
				if(dismiss)
					dismiss()
			}
		})
	}

	private val saveClickListener = View.OnClickListener {
		viewModel.saveBook(input.text.toString())
	}

	private val deleteClickListener = View.OnClickListener {
		viewModel.deleteBook()
	}

	private val saveOnEnterListener: View.OnKeyListener =
		View.OnKeyListener { _: View, keyCode: Int, keyEvent: KeyEvent ->
			if((keyEvent.action == KeyEvent.ACTION_DOWN) && keyCode == KeyEvent.KEYCODE_ENTER) {
				viewModel.saveBook(input.text.toString())
			}
			/* This is false so that the event isn't consumed and other buttons (such as the back button)
			 * can be pressed */
			false
		}
}
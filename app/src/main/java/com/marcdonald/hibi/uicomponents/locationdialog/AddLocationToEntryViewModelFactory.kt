package com.marcdonald.hibi.uicomponents.locationdialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marcdonald.hibi.data.repository.EntryRepository

class AddLocationToEntryViewModelFactory(private val entryRepository: EntryRepository)
	: ViewModelProvider.NewInstanceFactory() {

	@Suppress("UNCHECKED_CAST")
	override fun <T : ViewModel?> create(modelClass: Class<T>): T {
		return AddLocationToEntryViewModel(entryRepository) as T
	}
}
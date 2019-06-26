package com.marcdonald.hibi.mainscreens.throwbackscreen.throwbackentriesscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marcdonald.hibi.data.repository.BookEntryRelationRepository
import com.marcdonald.hibi.data.repository.EntryRepository
import com.marcdonald.hibi.data.repository.TagEntryRelationRepository

class ThrowbackEntriesViewModelFactory(private val entryRepository: EntryRepository,
                                       private val tagEntryRelationRepository: TagEntryRelationRepository,
                                       private val bookEntryRelationRepository: BookEntryRelationRepository)
  : ViewModelProvider.NewInstanceFactory() {

  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return ThrowbackEntriesViewModel(entryRepository, tagEntryRelationRepository, bookEntryRelationRepository) as T
  }
}
package com.livemachine.academy.ui.screens.glossary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.livemachine.academy.data.model.GlossaryCategory
import com.livemachine.academy.data.model.GlossaryTerm
import com.livemachine.academy.data.repository.CourseRepository
import com.livemachine.academy.ui.appViewModelFactory
import com.livemachine.academy.ui.components.AppTopBar
import com.livemachine.academy.ui.components.EmptyState
import com.livemachine.academy.ui.components.Eyebrow
import com.livemachine.academy.ui.components.ManualRefLine
import com.livemachine.academy.ui.components.SectionCard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class GlossaryViewModel(course: CourseRepository) : ViewModel() {
    private val all = course.glossary()
    val query = MutableStateFlow("")
    val category = MutableStateFlow<GlossaryCategory?>(null)

    val terms: StateFlow<List<GlossaryTerm>> = combine(query, category) { q, cat ->
        all.filter { term ->
            (cat == null || term.category == cat) &&
                (q.isBlank() || term.term.contains(q, ignoreCase = true) || term.definition.contains(q, ignoreCase = true))
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), all)

    companion object {
        val Factory = appViewModelFactory { c, _ -> GlossaryViewModel(c.courseRepository) }
    }
}

@Composable
fun GlossaryScreen(
    onOpenSettings: () -> Unit,
    viewModel: GlossaryViewModel = viewModel(factory = GlossaryViewModel.Factory),
) {
    val query by viewModel.query.collectAsStateWithLifecycle()
    val category by viewModel.category.collectAsStateWithLifecycle()
    val terms by viewModel.terms.collectAsStateWithLifecycle()

    Column(Modifier.fillMaxSize()) {
        AppTopBar(title = "Glossaire", onOpenSettings = onOpenSettings)
        OutlinedTextField(
            value = query,
            onValueChange = { viewModel.query.value = it },
            leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = null) },
            placeholder = { Text("Rechercher un terme") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
        ) {
            item {
                FilterChip(selected = category == null, onClick = { viewModel.category.value = null }, label = { Text("Tout") })
            }
            items(GlossaryCategory.entries) { cat ->
                FilterChip(
                    selected = category == cat,
                    onClick = { viewModel.category.value = if (category == cat) null else cat },
                    label = { Text(cat.label) },
                )
            }
        }
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            if (terms.isEmpty()) item { EmptyState("Aucun résultat", "Essaie un autre mot.") }
            items(terms, key = { it.term }) { term ->
                SectionCard {
                    Eyebrow(term.category.label, color = MaterialTheme.colorScheme.secondary)
                    Spacer(Modifier.height(4.dp))
                    Text(term.term, style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(4.dp))
                    Text(term.definition, style = MaterialTheme.typography.bodyMedium)
                    term.manualRef?.let { Spacer(Modifier.height(4.dp)); ManualRefLine(it) }
                }
            }
        }
    }
}

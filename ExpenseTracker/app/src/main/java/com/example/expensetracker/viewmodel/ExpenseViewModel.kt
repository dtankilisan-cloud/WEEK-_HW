package com.example.expensetracker.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.expensetracker.model.Expense

class ExpenseViewModel : ViewModel() {
    private val _expenses = mutableStateListOf<Expense>()
    val expenses: List<Expense> get() = _expenses
    private var nextId = 1
    var nameInput by mutableStateOf("")
        private set
    var amountInput by mutableStateOf("")
        private set
    var amountError by mutableStateOf<String?>(null)
        private set
    val totalExpense: Long get() = _expenses.sumOf { it.amount }
    val canAdd: Boolean get() = nameInput.isNotBlank() && amountInput.isNotBlank() && amountError == null
    var editingExpense by mutableStateOf<Expense?>(null)
        private set
    var editInput by mutableStateOf("")
        private set
    var editError by mutableStateOf<String?>(null)
        private set
    private fun validate(text: String): String? {
        if (text.isEmpty()) return null
        val value = text.toLongOrNull() ?: return "Invalid amount"
        return if (value <= 0L) "Amount must be greater than 0" else null
    }
    fun onNameChange(value: String) {
        nameInput = value
    }
    fun onAmountChange(value: String) {
        amountInput = value.filter { it.isDigit() }.take(12)
        amountError = validate(amountInput)
    }
    fun addExpense() {
        val error = validate(amountInput)
        if (error != null || nameInput.isBlank() || amountInput.isEmpty()) {
            amountError = error
            return
        }
        _expenses.add(Expense(nextId++, nameInput.trim(), amountInput.toLong()))
        nameInput = ""
        amountInput = ""
        amountError = null
    }
    fun deleteExpense(expense: Expense) {
        _expenses.removeAll { it.id == expense.id }
    }
    fun startEdit(expense: Expense) {
        editingExpense = expense
        editInput = expense.amount.toString()
        editError = null
    }
    fun onEditChange(value: String) {
        editInput = value.filter { it.isDigit() }.take(12)
        editError = validate(editInput)
    }
    fun confirmEdit() {
        val target = editingExpense ?: return
        val error = validate(editInput)
        if (error != null || editInput.isEmpty()) {
            editError = error ?: "Amount is required"
            return
        }
        val index = _expenses.indexOfFirst { it.id == target.id }
        if (index >= 0) _expenses[index] = target.copy(amount = editInput.toLong())
        cancelEdit()
    }
    fun cancelEdit() {
        editingExpense = null
        editInput = ""
        editError = null
    }
}

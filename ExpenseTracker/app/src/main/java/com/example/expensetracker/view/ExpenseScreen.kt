package com.example.expensetracker.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.expensetracker.model.Expense
import com.example.expensetracker.viewmodel.ExpenseViewModel

fun formatRupiah(value: Long): String = "Rp " + "%,d".format(value).replace(',', '.')

@Composable
fun ExpenseScreen(vm: ExpenseViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp)
    ) {
        Text("Expenses", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text("Total Expense : ${formatRupiah(vm.totalExpense)}", fontSize = 12.sp)
        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = vm.nameInput,
            onValueChange = vm::onNameChange,
            label = { Text("Expense Name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.Top) {
            OutlinedTextField(
                value = vm.amountInput,
                onValueChange = vm::onAmountChange,
                label = { Text("Amount of Expense") },
                singleLine = true,
                isError = vm.amountError != null,
                supportingText = { vm.amountError?.let { Text(it) } },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(8.dp))
            FilledTonalIconButton(
                onClick = vm::addExpense,
                enabled = vm.canAdd,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
        Spacer(Modifier.height(8.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(vm.expenses, key = { it.id }) { expense ->
                ExpenseItem(
                    expense = expense,
                    onEdit = { vm.startEdit(expense) },
                    onDelete = { vm.deleteExpense(expense) }
                )
            }
        }
    }

    if (vm.editingExpense != null) {
        AlertDialog(
            onDismissRequest = vm::cancelEdit,
            title = { Text("Edit Amount") },
            text = {
                OutlinedTextField(
                    value = vm.editInput,
                    onValueChange = vm::onEditChange,
                    label = { Text("Amount of Expense") },
                    singleLine = true,
                    isError = vm.editError != null,
                    supportingText = { vm.editError?.let { Text(it) } },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            },
            confirmButton = { TextButton(onClick = vm::confirmEdit) { Text("Save") } },
            dismissButton = { TextButton(onClick = vm::cancelEdit) { Text("Cancel") } }
        )
    }
}

@Composable
fun ExpenseItem(expense: Expense, onEdit: () -> Unit, onDelete: () -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFDDE1F5)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(start = 12.dp, end = 4.dp, top = 4.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text("Name : ${expense.name}", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Text(formatRupiah(expense.amount), fontSize = 11.sp)
            }
            IconButton(onClick = onEdit) {
                Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color(0xFFD4A017))
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color(0xFFC62828))
            }
        }
    }
}

@Preview(showBackground = true, name = "Expenses - list")
@Composable
fun ExpenseScreenPreview() {
    val vm = ExpenseViewModel().apply {
        onNameChange("Makan Nasi"); onAmountChange("90000"); addExpense()
        onNameChange("Investasi"); onAmountChange("10000000"); addExpense()
        onNameChange("Kopi"); onAmountChange("18000"); addExpense()
    }
    MaterialTheme { ExpenseScreen(vm) }
}

@Preview(showBackground = true, name = "Expenses - error")
@Composable
fun ExpenseScreenErrorPreview() {
    val vm = ExpenseViewModel().apply {
        onNameChange("Makan Nasi"); onAmountChange("90000"); addExpense()
        onNameChange("Error Handling"); onAmountChange("0")
    }
    MaterialTheme { ExpenseScreen(vm) }
}

@Preview(showBackground = true, name = "Expense item")
@Composable
fun ExpenseItemPreview() {
    MaterialTheme { ExpenseItem(Expense(1, "Kopi", 18000), onEdit = {}, onDelete = {}) }
}

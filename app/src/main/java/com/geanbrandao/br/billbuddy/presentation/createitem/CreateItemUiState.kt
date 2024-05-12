package com.geanbrandao.br.billbuddy.presentation.createitem

import android.os.Parcelable
import com.geanbrandao.br.billbuddy.domain.model.CreateItemModel
import com.geanbrandao.br.billbuddy.domain.model.CreateItemModel.DividedValue
import com.geanbrandao.br.billbuddy.domain.model.PersonModel
import com.geanbrandao.br.billbuddy.utils.MoneyExtensions.ZERO_MONEY
import com.geanbrandao.br.billbuddy.utils.MoneyExtensions.moneyBrlToFloat
import kotlinx.parcelize.Parcelize

@Parcelize
data class CreateItemUiState(
    val billId: Long = -1,
    val name: String = "",
    val value: String = ZERO_MONEY,
    val persons: List<PersonModel> = listOf(),
    val isConfirmationDialogOpen: Boolean = false,
) : Parcelable {
    val isEnabled: Boolean
        get() = isValidName() && isValidValue() && isValidPersons()

    val resume: CreateItemModel
        get() = createResume()

    private fun isValidName(): Boolean = this.name.trim().isNotEmpty()
    private fun isValidValue(): Boolean = this.value != ZERO_MONEY
    private fun isValidPersons(): Boolean = persons.any { it.isChecked }

    private fun createResume(): CreateItemModel {
        val list = persons.filter { it.isChecked }
        return CreateItemModel(
            itemName = name,
            itemValue = value.moneyBrlToFloat(),
            billId = billId,
            dividedValues = divideItemBetweenPersons(
                itemValue = value.moneyBrlToFloat(),
                list = list
            )
        )
    }

    private fun divideItemBetweenPersons(itemValue: Float, list: List<PersonModel>): List<DividedValue> {
        val dividedValues = arrayListOf<DividedValue>()
        if (list.isEmpty()) {
            throw Exception("Nenhuma pessa selecionada para dividir")
        }

        // Convert to cents
        val centValue = (itemValue * 100).toInt()

        // Calcula a parte inteira da divisão em centavos
        val quotient = centValue / list.size

        // Calcula o resto da divisão em centavos
        val remainder = centValue % list.size

        // Distribui o valor igual entre os usuários
        list.forEachIndexed { index, personModel ->
            val value = if (index < remainder) {
                quotient + 1
            } else {
                quotient
            }
            dividedValues.add(
                DividedValue(
                    personId = personModel.id,
                    dividedValue = value / 100f,
                    personName = personModel.name
                )
            )
        }

        return dividedValues
    }
}

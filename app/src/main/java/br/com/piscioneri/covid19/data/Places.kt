package br.com.piscioneri.covid19.data

object Places {
    fun toListDescription(): List<String> {
        val list = ArrayList<String>()
        tolist().forEach {
            list.add(it.value)
        }
        return list
    }

    fun getCode(description: String): String {
        tolist().map {
            if (it.value == description) return it.key.toLowerCase()
        }
        return ""
    }

    private fun tolist(): HashMap<String, String> {
        val places = HashMap<String, String>()

        places["BR"] = "Brasil"
        places["AC"] = "Acre"
        places["AL"] = "Alagoas"
        places["AP"] = "Amapá"
        places["AM"] = "Amazonas"
        places["BA"] = "Bahia"
        places["CE"] = "Ceará"
        places["DF"] = "Distrito Federal"
        places["ES"] = "Espírito Santo"
        places["GO"] = "Goiás"
        places["MA"] = "Maranhão"
        places["MT"] = "Mato Grosso"
        places["MS"] = "Mato Grosso do Sul"
        places["MG"] = "Minas Gerais"
        places["PA"] = "Pará"
        places["PB"] = "Paraíba"
        places["PR"] = "Paraná"
        places["PE"] = "Pernambuco"
        places["PI"] = "Piauí"
        places["RJ"] = "Rio de Janeiro"
        places["RN"] = "Rio Grande do Norte"
        places["RS"] = "Rio Grande do Sul"
        places["RO"] = "Rondônia"
        places["RR"] = "Roraima"
        places["SC"] = "Santa Catarina"
        places["SP"] = "São Paulo"
        places["SE"] = "Sergipe"
        places["TO"] = "Tocantins"

        return places
    }
}

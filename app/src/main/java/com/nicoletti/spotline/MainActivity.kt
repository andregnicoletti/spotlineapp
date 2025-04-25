package com.nicoletti.spotline // Pacote do seu app, sempre começa o arquivo com isso

// Importações necessárias
import android.Manifest // Permissão de localização
import android.content.pm.PackageManager // Para verificar se a permissão foi concedida
import android.os.Bundle // Bundle é usado no ciclo de vida da Activity
import android.widget.Button // Para usar o botão
import android.widget.Toast // Para mostrar mensagens rápidas na tela
import androidx.appcompat.app.AppCompatActivity // Base das telas (Activity) modernas
import androidx.core.app.ActivityCompat // Para solicitar permissões
import androidx.core.content.ContextCompat // Para verificar permissões

class MainActivity : AppCompatActivity() { // A classe da sua tela principal

    // Código da permissão que usaremos para identificar o retorno
    private val LOCATION_PERMISSION_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // Chama o comportamento padrão
        setContentView(R.layout.activity_main) // Define qual layout XML essa tela usa

        // Pega a referência do botão do layout usando o ID
        val startButton = findViewById<Button>(R.id.startButton)

        // Quando clicar no botão...
        startButton.setOnClickListener {
            // ...verifica se a permissão já foi concedida
            if (hasLocationPermission()) {
                // Se sim, avisa o usuário
                Toast.makeText(this, "Permissão já concedida!", Toast.LENGTH_SHORT).show()
                // Aqui depois a gente vai chamar o worker para rastrear
            } else {
                // Se não tiver, pede a permissão
                requestLocationPermission()
            }
        }
    }

    // Função para verificar se já temos permissão de localização
    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this, // contexto da Activity
            Manifest.permission.ACCESS_FINE_LOCATION // tipo de permissão
        ) == PackageManager.PERMISSION_GRANTED // retorna true se já tiver permissão
    }

    // Função que solicita a permissão ao usuário
    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this, // contexto
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), // lista de permissões que queremos
            LOCATION_PERMISSION_CODE // código para saber quem está pedindo
        )
    }

    // Função chamada automaticamente quando o usuário responde a permissão
    override fun onRequestPermissionsResult(
        requestCode: Int, // quem está pedindo
        permissions: Array<out String>, // lista das permissões solicitadas
        grantResults: IntArray // resultado de cada permissão (concedida ou não)
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // Se for a nossa permissão de localização...
        if (requestCode == LOCATION_PERMISSION_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // ...e ela foi concedida
            Toast.makeText(this, "Permissão concedida!", Toast.LENGTH_SHORT).show()
            // Aqui depois vamos iniciar o worker
        } else {
            // Se o usuário recusou
            Toast.makeText(this, "Permissão negada!", Toast.LENGTH_SHORT).show()
        }
    }
}

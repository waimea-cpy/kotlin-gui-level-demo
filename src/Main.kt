/**
 * ===============================================================
 * Kotlin GUI Starter
 * ===============================================================
 *
 * This is a starter project for a simple Kotlin GUI application.
 * The Java Swing library is used, plus the FlatLAF look-and-feel
 * for a reasonably modern look.
 *
 * The app is structured to provide a simple view / model setup
 * with the App class storing application data (the 'model'), and
 * the MainWindow class providing the 'view'.
 */

import com.formdev.flatlaf.FlatDarkLaf
import java.awt.*
import java.awt.event.*
import javax.swing.*


/**
 * Launch the application
 */
fun main() {
    FlatDarkLaf.setup()     // Flat, dark look-and-feel
    val app = App()         // Create the app model
    MainWindow(app)         // Create and show the UI, using the app model
}


/**
 * The application class (model)
 * This is the place where any application data should be
 * stored, plus any application logic functions
 */
class App() {
    // Constants defining any key values
    val MAX_CLICKS = 10

    // Data fields
    var clicks = 0

    // Application logic functions
    fun updateClickCount() {
        clicks++
        if (clicks > MAX_CLICKS) clicks = MAX_CLICKS
    }
}


/**
 * Main UI window (view)
 * Defines the UI and responds to events
 * The app model should be passwd as an argument
 */
class MainWindow(val app: App) : JFrame(), ActionListener {

    // Fields to hold the UI elements
    private lateinit var infoLabel: JLabel
    private lateinit var helloButton: JButton

    /**
     * Configure the UI and display it
     */
    init {
        configureWindow()               // Configure the window
        addControls()                   // Build the UI

        setLocationRelativeTo(null)     // Centre the window
        isVisible = true                // Make it visible

        updateView()                    // Initialise view with model data
    }

    /**
     * Configure the main window
     */
    private fun configureWindow() {
        title = "Kotlin Swing GUI Demo"
        contentPane.preferredSize = Dimension(600, 350)
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        isResizable = false
        layout = null

        pack()
    }

    /**
     * Populate the UI with UI controls
     */
    private fun addControls() {
        val baseFont = Font(Font.SANS_SERIF, Font.PLAIN, 36)

        infoLabel = JLabel("INFO HERE")
        infoLabel.horizontalAlignment = SwingConstants.CENTER
        infoLabel.bounds = Rectangle(50, 50, 500, 100)
        infoLabel.font = baseFont
        add(infoLabel)

        helloButton = JButton("Click Me!")
        helloButton.bounds = Rectangle(50,200,500,100)
        helloButton.font = baseFont
        helloButton.addActionListener(this)     // Handle any clicks
        add(helloButton)
    }


    /**
     * Update the UI controls based on the current state
     * of the application model
     */
    fun updateView() {
        if (app.clicks == app.MAX_CLICKS) {
            infoLabel.text = "Max clicks reached!"
            helloButton.isEnabled = false
        }
        else {
            infoLabel.text = "You clicked ${app.clicks} times"
        }
    }

    /**
     * Handle any UI events (e.g. button clicks)
     * Usually this involves updating the application model
     * then refreshing the UI view
     */
    override fun actionPerformed(e: ActionEvent?) {
        when (e?.source) {
            helloButton -> {
                app.updateClickCount()
                updateView()
            }
        }
    }

}


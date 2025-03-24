/**
 * ===============================================================
 * Kotlin GUI Level Meter Demo
 * ===============================================================
 *
 * This is a demo showing how a Kotlin / Swing GUI app can show
 * a graphical representation of a value in the app model data.
 *
 * A panel is placed within another container / back panel, and
 * resized based on the data value.
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
    // Constants
    val MAX_VOL = 10
    val MIN_VOL = 0

    // Data fields
    var volume = MAX_VOL / 2

    // Application logic functions
    fun increaseVolume() {
        volume++
        if (volume > MAX_VOL) volume = MAX_VOL
    }

    fun decreaseVolume() {
        volume--
        if (volume < MIN_VOL) volume = MIN_VOL
    }
}


/**
 * Main UI window (view)
 * Defines the UI and responds to events
 * The app model should be passwd as an argument
 */
class MainWindow(val app: App) : JFrame(), ActionListener {

    // Fields to hold the UI elements
    private lateinit var volBackPanel: JPanel
    private lateinit var volLevelPanel: JPanel
    private lateinit var upButton: JButton
    private lateinit var downButton: JButton

    /**
     * Configure the UI and display it
     */
    init {
        configureWindow()               // Configure the window
        addControls()                   // Build the UI

        setLocationRelativeTo(null)     // Centre the window
        isVisible = true                // Make it visible

        updateView()                    // Initialise the view with any app data
    }

    /**
     * Configure the main window
     */
    private fun configureWindow() {
        title = "Kotlin Swing GUI Level Demo"
        contentPane.preferredSize = Dimension(375, 225)
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        isResizable = false
        layout = null

        pack()
    }

    /**
     * Populate the UI with UI controls
     */
    private fun addControls() {
        val baseFont = Font(Font.SANS_SERIF, Font.PLAIN, 24)

        // This panel acts as the 'back' of the level meter
        volBackPanel = JPanel()
        volBackPanel.bounds = Rectangle(25, 25, 325, 100)
        volBackPanel.background = Color.BLACK
        volBackPanel.layout = null                // Want layout to be manual
        add(volBackPanel)

        // And this one sits inside the one above to make resizing it easier
        volLevelPanel = JPanel()
        volLevelPanel.bounds = Rectangle(0, 0, 325, 100)
        volLevelPanel.background = Color.YELLOW
        volBackPanel.add(volLevelPanel)           // Add this panel inside the other

        downButton = JButton("Down")
        downButton.bounds = Rectangle(25, 150, 150, 50)
        downButton.font = baseFont
        downButton.addActionListener(this)     // Handle any clicks
        add(downButton)

        upButton = JButton("Up")
        upButton.bounds = Rectangle(200, 150, 150, 50)
        upButton.font = baseFont
        upButton.addActionListener(this)     // Handle any clicks
        add(upButton)
    }


    /**
     * Update the UI controls based on the current state
     * of the application model
     */
    fun updateView() {
        // Sizes of the volume bar
        val volWidth = calcVolumePanelWidth()
        val volHeight = volBackPanel.size.height

        // Update the bar's size
        volLevelPanel.bounds = Rectangle(0, 0, volWidth, volHeight)
    }

    /**
     * Work out the volume bar width based on the parent back panel's width
     */
    fun calcVolumePanelWidth(): Int {
        val volFraction = app.volume.toDouble() / app.MAX_VOL   // Volume from 0.0 to 1.0
        val maxWidth = volBackPanel.bounds.width                // Size of background panel
        val volWidth = (maxWidth * volFraction).toInt()         // Size in px
        return volWidth
    }

    /**
     * Handle any UI events (e.g. button clicks)
     * Usually this involves updating the application model
     * then refreshing the UI view
     */
    override fun actionPerformed(e: ActionEvent?) {
        // Update app data model based on button clicks
        when (e?.source) {
            upButton -> app.increaseVolume()
            downButton -> app.decreaseVolume()
        }

        // And ensure the UI view matched the updated app model data
        updateView()
    }
}


class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var map: GoogleMap
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var noQuestsMessage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        // Initialize views
        bottomNav = findViewById(R.id.bottomNav)
        noQuestsMessage = findViewById(R.id.noQuestsMessage)

        // Initialize map
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Set up bottom navigation
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_hamburger -> {
                    // Handle menu click
                    true
                }
                R.id.menu_map -> {
                    // Handle map click
                    true
                }
                R.id.menu_profile -> {
                    // Handle profile click
                    true
                }
                else -> false
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // Set initial position (Saint Petersburg coordinates)
        val spb = LatLng(59.9311, 30.3609)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(spb, 15f))

        // Customize map settings
        map.uiSettings.apply {
            isZoomControlsEnabled = false // We have custom zoom buttons
            isMyLocationButtonEnabled = true
            isCompassEnabled = true
        }

        // Set custom zoom controls
        findViewById<Button>(R.id.zoomInButton).setOnClickListener {
            map.animateCamera(CameraUpdateFactory.zoomIn())
        }
        findViewById<Button>(R.id.zoomOutButton).setOnClickListener {
            map.animateCamera(CameraUpdateFactory.zoomOut())
        }
    }
}
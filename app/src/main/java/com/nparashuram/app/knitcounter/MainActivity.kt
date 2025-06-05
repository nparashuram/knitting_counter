package com.nparashuram.app.knitcounter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nparashuram.app.knitcounter.ui.theme.CounterTheme
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.Image
import androidx.compose.animation.core.animateDpAsState
import kotlinx.coroutines.delay
import androidx.compose.ui.platform.LocalDensity
import androidx.lifecycle.lifecycleScope
import com.nparashuram.app.knitcounter.data.CounterPreferences
import kotlinx.coroutines.launch

// ... existing code ... 
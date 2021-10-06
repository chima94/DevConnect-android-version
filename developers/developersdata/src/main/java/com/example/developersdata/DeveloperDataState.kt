package com.example.developersdata

import com.example.networkresponses.developerreponse.DeveloperModel
import com.example.util.Queue
import com.example.util.StateMessage

data class DeveloperDataState(
    var isLoading: Boolean = false,
    var data: DeveloperModel? = null,
    var queue: Queue<StateMessage> = Queue(mutableListOf())
)


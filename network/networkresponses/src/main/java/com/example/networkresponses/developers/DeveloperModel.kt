package com.example.networkresponses.developers

class DeveloperModel : ArrayList<DeveloperModelItem>()

fun DeveloperModel.toDevelopers() : List<Developer>{
    return this.map {
        Developer(
            id = it._id,
            name = it.user.name,
            avatar = it.user.avatar,
            company = it.company
        )
    }
}
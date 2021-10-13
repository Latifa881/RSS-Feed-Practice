package com.example.rssfeedpractice

class Details {

    var rank: Int? =0
    var title:String?=null
    var author:String?=null
    var published:String?=null
    var updated:String?=null
    var summary:String?=null

    constructor(rank: Int?, title:String?, author:String?, published:String?, updated:String?, summary:String?){
        this.rank=rank
        this.title=title
        this.author=author
        this.published=published
        this.updated=updated
        this.summary=summary
    }

}
package com.template

import com.template.model.DomainState

interface DomainIntent{
    class Data:DomainIntent
    class Error (val message: DomainState):DomainIntent
    class Seve(val domain: DomainState):DomainIntent
}

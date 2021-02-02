package com.papuguys.camera.graphqlapi.exceptions

import graphql.ErrorType
import graphql.GraphQLError
import graphql.language.SourceLocation


class ExternalPhotoServerError(@JvmField override val message: String?) : RuntimeException(message), GraphQLError {

    override fun getMessage(): String? = super.message

    override fun getLocations(): MutableList<SourceLocation>? {
        return null
    }

    override fun getErrorType(): ErrorType? {
        return null
    }
}

//@Component
//class CustomGraphQLErrorHandler : GraphQLErrorHandler {
//    override fun processErrors(list: List<GraphQLError>): List<GraphQLError> {
//        return list.stream().map { error: GraphQLError ->
//            getNested(error)
//        }.collect(Collectors.toList())
//    }
//
//    private fun getNested(error: GraphQLError): GraphQLError {
//        if (error is ExceptionWhileDataFetching) {
//            if (error.exception is ExternalPhotoServerError) {
//                return error.exception as GraphQLError
//            }
//        }
//        return error
//    }
//}
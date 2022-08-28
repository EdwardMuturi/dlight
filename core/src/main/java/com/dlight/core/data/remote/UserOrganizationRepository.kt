import android.nfc.tech.MifareUltralight.PAGE_SIZE
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Input
import com.dlight.UserOrOrganizationQuery
import com.dlight.core.network.ApiFailure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class UserOrganizationRepository(
    private val apolloClient: ApolloClient
) {

    /*suspend fun fetchUserOrOrganization(
        login: String,
        cursor: String?,
        onFailure: (ApiFailure) -> Unit
    ): Flow<UserOrOrganization?> {
        val data = apolloClient.query(
            UserOrOrganizationQuery(
                login,
                PAGE_SIZE,
                Input.fromNullable(cursor)
            )
        ).toDeferred().data(onFailure)
            ?: return flowOf()
        return flowOf(
            data.repositoryOwner?.let {
                when {
                    it.asUser != null ->
                        UserOrOrganization(it.asUser.fragments.userProfileFragment)
                    it.asOrganization != null ->
                        UserOrOrganization(it.asOrganization.fragments.organizationFragment)
                    else -> null
                }
            }
        )
    }*/
}
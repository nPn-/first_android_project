package com.gmail.npnster.first_project;

import javax.inject.Inject;

import retrofit.RetrofitError;
import retrofit.client.Response;
import android.content.Context;
import android.content.Intent;

import com.gmail.npnster.first_project.RailsApiClientSync.RailsApiSync;
import com.gmail.npnster.first_project.api_params.CreateDeviceRequest;
import com.gmail.npnster.first_project.api_params.CreateDeviceResponse;
import com.gmail.npnster.first_project.api_params.GetMapMarkersRequest;
import com.gmail.npnster.first_project.api_params.GetMapMarkersResponse;
import com.gmail.npnster.first_project.api_params.GrantFollowerPermissionRequest;
import com.gmail.npnster.first_project.api_params.GrantFollowerPermissionResponse;
import com.gmail.npnster.first_project.api_params.CreateMicropostRequest;
import com.gmail.npnster.first_project.api_params.CreateMicropostResponse;
import com.gmail.npnster.first_project.api_params.DeleteMicropostRequest;
import com.gmail.npnster.first_project.api_params.DeleteMicropostResponse;
import com.gmail.npnster.first_project.api_params.FollowRequest;
import com.gmail.npnster.first_project.api_params.FollowResponse;
import com.gmail.npnster.first_project.api_params.GetFollowedUsersResponse;
import com.gmail.npnster.first_project.api_params.GetFollowersRequest;
import com.gmail.npnster.first_project.api_params.GetFollowersResponse;
import com.gmail.npnster.first_project.api_params.GetFollowedUsersRequest;
import com.gmail.npnster.first_project.api_params.GetMicropostsRequest;
import com.gmail.npnster.first_project.api_params.GetMicropostsResponse;
import com.gmail.npnster.first_project.api_params.GetUserProfileRequest;
import com.gmail.npnster.first_project.api_params.GetUserProfileResponse;
import com.gmail.npnster.first_project.api_params.GetUsersRequest;
import com.gmail.npnster.first_project.api_params.GetUsersResponse;
import com.gmail.npnster.first_project.api_params.LeaveRequest;
import com.gmail.npnster.first_project.api_params.LeaveResponse;
import com.gmail.npnster.first_project.api_params.PatchLocationRequest;
import com.gmail.npnster.first_project.api_params.PatchLocationResponse;
import com.gmail.npnster.first_project.api_params.PostLocationRequest;
import com.gmail.npnster.first_project.api_params.PostLocationResponse;
import com.gmail.npnster.first_project.api_params.PushLocationsUpdateRequestRequest;
import com.gmail.npnster.first_project.api_params.PushLocationsUpdateRequestResponse;
import com.gmail.npnster.first_project.api_params.RevokeFollowerPermissionRequest;
import com.gmail.npnster.first_project.api_params.RevokeFollowerPermissionResponse;
import com.gmail.npnster.first_project.api_params.SigninRequest;
import com.gmail.npnster.first_project.api_params.SigninResponse;
import com.gmail.npnster.first_project.api_params.SignoutRequest;
import com.gmail.npnster.first_project.api_params.SignoutResponse;
import com.gmail.npnster.first_project.api_params.SignupRequest;
import com.gmail.npnster.first_project.api_params.SignupResponse;
import com.gmail.npnster.first_project.api_params.UnfollowRequest;
import com.gmail.npnster.first_project.api_params.UnfollowResponse;
import com.gmail.npnster.first_project.api_params.UpdateUserRequest;
import com.gmail.npnster.first_project.api_params.UpdateUserResponse;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class ApiRequestRepositorySync {

	private RailsApiSync mRailsApi;
	PersistData mPersistData;
	protected Bus mBus;
	@Inject RailsApiQue mRailsApiQue;
	@Inject @ForApplication Context mContext;
	Intent mProcessQueEntry;

	public ApiRequestRepositorySync(PersistData persistData, RailsApiSync railsApi, Bus bus) {
		mPersistData = persistData;
		mRailsApi = railsApi;
		mBus = bus;
		Injector.getInstance().inject(this);
		mProcessQueEntry = new Intent(mContext, ApiIntentService.class);
		
	}
	
	private void submitRequest(RailsApiQueEntry<?,?> entry) {
		mRailsApiQue.offer(entry);
		System.out.println(String.format("api gue entry count = %d" , mRailsApiQue.size()));
		mContext.startService(mProcessQueEntry);
	}

	@Subscribe
	public void onUserProfileRequestEvent(GetUserProfileRequest event) {
		final String userToFetch = event.getUserId() == null ? mPersistData.getUserId() : event.getUserId();
		System.out.println(String.format("inside api repro making profile request with the following userParams,  user = %s, token = %s",userToFetch, mPersistData.getToken()));
		GetUserProfileResponse response = new GetUserProfileResponse();
		RailsApiQueEntry<GetUserProfileRequest,GetUserProfileResponse> railsApiQueEntry = new RailsApiQueEntry<GetUserProfileRequest,GetUserProfileResponse>(mBus, mRailsApi, event, response) {

			@Override
			public Response makeRequest() throws RetrofitError {
				Response response = mRailsApiSync.userParams(userToFetch, mPersistData.getToken());			
				return response;
			}			
		};
		submitRequest(railsApiQueEntry);
	}
		

	@Subscribe
	public void onSignout(SignoutRequest event) {
		System.out.println("inside api repo - making signout request");
		final String token = event.getToken() == null ? mPersistData.getToken() : event.getToken();
		final String email = event.getEmail() == null ? mPersistData.getEmail() : event.getEmail();
		final String password = event.getPassword() == null ? "" : event.getPassword();
		SignoutResponse response = new SignoutResponse();
		RailsApiQueEntry<SignoutRequest,SignoutResponse> railsApiQueEntry = new RailsApiQueEntry<SignoutRequest,SignoutResponse>(mBus, mRailsApi, event, response) {

			@Override
			public Response makeRequest() throws RetrofitError {
			
				Response response = mRailsApiSync.signout(token, email, password);
				return response;
			}
		};
		submitRequest(railsApiQueEntry);
	}

	@Subscribe
	public void onLeave(final LeaveRequest event) {
		System.out.println("inside api repo - making leave request");
		LeaveResponse response = new LeaveResponse();
		RailsApiQueEntry<LeaveRequest,LeaveResponse> railsApiQueEntry = new RailsApiQueEntry<LeaveRequest,LeaveResponse>(mBus, mRailsApi, event, response) {

			@Override
			public Response makeRequest() throws RetrofitError {
				Response response = mRailsApiSync.leave(event.getEmail(), event.getPassword());
				return response;
			}			
		};
		submitRequest(railsApiQueEntry);
	}

	@Subscribe
	public void onSignup(final SignupRequest event) {
		System.out.println("inside api repo - making signup request");
		SignupResponse response = new SignupResponse();
		RailsApiQueEntry<SignupRequest,SignupResponse> railsApiQueEntry = new RailsApiQueEntry<SignupRequest,SignupResponse>(mBus, mRailsApi, event, response) {

			@Override
			public Response makeRequest() throws RetrofitError {
				Response response = mRailsApiSync.signup(event);
				return response;
			}
		};
		submitRequest(railsApiQueEntry);
	}

	@Subscribe
	public void onSignin(final SigninRequest event) {
		System.out.println("inside api repo - making signin request");
		SigninResponse response = new SigninResponse();
		RailsApiQueEntry<SigninRequest,SigninResponse> railsApiQueEntry = new RailsApiQueEntry<SigninRequest,SigninResponse>(mBus, mRailsApi, event, response) {

			@Override
			public Response makeRequest() throws RetrofitError {
				Response response = mRailsApiSync.signin(event);
				return response;
			}
		};
		submitRequest(railsApiQueEntry);
	}

	@Subscribe
	public void onGetUsers(GetUsersRequest event) {
		final String pageNumber = event.getRequestedPageNumber() == -1 ? "1" : String.valueOf(event.getRequestedPageNumber());
		final String perPage = event.getRequestedItemsPerPage() == -1 ? "all" : String.valueOf(event.getRequestedItemsPerPage());
		final String search = event.getSearch();
		System.out.println(String.format("inside api repo - making get users list  request page = %s, items per page = %s",
				pageNumber, perPage));
		GetUsersResponse response = new GetUsersResponse();
		RailsApiQueEntry<GetUsersRequest,GetUsersResponse> railsApiQueEntry = new RailsApiQueEntry<GetUsersRequest,GetUsersResponse>(mBus, mRailsApi, event, response) {

			@Override
			public Response makeRequest() throws RetrofitError {
				Response response = mRailsApiSync.getUsers(mPersistData.getToken(), pageNumber, perPage, search);
				return response;
			}
		};
		submitRequest(railsApiQueEntry);
	}

	@Subscribe
	public void onGetFollowers(final GetFollowersRequest event) {
		System.out.println("inside api repo - making get followers list  request");
		GetFollowersResponse response = new GetFollowersResponse();
		RailsApiQueEntry<GetFollowersRequest,GetFollowersResponse> railsApiQueEntry = new RailsApiQueEntry<GetFollowersRequest,GetFollowersResponse>(mBus, mRailsApi, event, response) {

			@Override
			public Response makeRequest() throws RetrofitError {
				Response response = mRailsApiSync.getFollowers(mPersistData.getToken(), event.getId());
				return response;
			}			
		};
		submitRequest(railsApiQueEntry);
	}

	@Subscribe
	public void onGetFollowedUsers(final GetFollowedUsersRequest event) {
		System.out.println("inside api repo - making get followed users list  request");
		final String token = event.getToken() != null ? event.getToken() : mPersistData.getToken();
		GetFollowedUsersResponse response = new GetFollowedUsersResponse();
		RailsApiQueEntry<GetFollowedUsersRequest,GetFollowedUsersResponse> railsApiQueEntry = new RailsApiQueEntry<GetFollowedUsersRequest,GetFollowedUsersResponse>(mBus, mRailsApi, event, response) {

			@Override
			public Response makeRequest() throws RetrofitError {
				Response response = mRailsApiSync.getFollowedUsers(token, event.getId());
				return response;
			}
		};
		submitRequest(railsApiQueEntry);
	}

	@Subscribe
	public void onGetMicroposts(final GetMicropostsRequest event) {
		System.out.println("inside api repo - making get user microposts list  request");
		final String token = event.getToken() != null ? event.getToken() : mPersistData.getToken();
		GetMicropostsResponse response = new GetMicropostsResponse();
		RailsApiQueEntry<GetMicropostsRequest,GetMicropostsResponse> railsApiQueEntry = new RailsApiQueEntry<GetMicropostsRequest,GetMicropostsResponse>(mBus, mRailsApi, event, response) {

			@Override
			public Response makeRequest() throws RetrofitError {
				Response response = mRailsApiSync.getMicroposts(token, event.getId());
				return response;
			}						
		};
		submitRequest(railsApiQueEntry);
	}

	@Subscribe
	public void onCreateMicropost(final CreateMicropostRequest event) {
		System.out.println("inside api repo - making create micropost  request");
		event.setApi_access_token(mPersistData.getToken());
		CreateMicropostResponse response = new CreateMicropostResponse();
		RailsApiQueEntry<CreateMicropostRequest,CreateMicropostResponse> railsApiQueEntry = new RailsApiQueEntry<CreateMicropostRequest,CreateMicropostResponse>(mBus, mRailsApi, event, response) {

			@Override
			public Response makeRequest() throws RetrofitError {
				Response response = mRailsApiSync.createMicropost(event);
				return response;
			}
		};
		submitRequest(railsApiQueEntry);
	}

	@Subscribe
	public void onDeleteMicropost(final DeleteMicropostRequest event) {
		System.out.println("inside api repo - making delete micropost  request");
		DeleteMicropostResponse response = new DeleteMicropostResponse();
		RailsApiQueEntry<DeleteMicropostRequest,DeleteMicropostResponse> railsApiQueEntry = new RailsApiQueEntry<DeleteMicropostRequest,DeleteMicropostResponse>(mBus, mRailsApi, event, response) {

			@Override
			public Response makeRequest() throws RetrofitError {
				Response response = mRailsApiSync.deleteMicropost(mPersistData.getToken(), event.getMicropostId());
				return response;
			}
		};
		submitRequest(railsApiQueEntry);
	}

	@Subscribe
	public void onFollow(final FollowRequest event) {
		System.out.println("inside api repo - making follow request");
		if (event.getToken() == null)
			event.setToken(mPersistData.getToken());
		FollowResponse response = new FollowResponse();
		RailsApiQueEntry<FollowRequest,FollowResponse> railsApiQueEntry = new RailsApiQueEntry<FollowRequest,FollowResponse>(mBus, mRailsApi, event, response) {

			@Override
			public Response makeRequest() throws RetrofitError {
				Response response = mRailsApiSync.follow(event.getUserId(), event);
				return response;
			}
		};
		submitRequest(railsApiQueEntry);
	}

	@Subscribe
	public void onUnfollow(final UnfollowRequest event) {
		System.out.println("inside api repo - making unfollow request");
		UnfollowResponse response = new UnfollowResponse();
		RailsApiQueEntry<UnfollowRequest,UnfollowResponse> railsApiQueEntry = new RailsApiQueEntry<UnfollowRequest,UnfollowResponse>(mBus, mRailsApi, event, response) {

			@Override
			public Response makeRequest() throws RetrofitError {
				Response response = mRailsApiSync.unfollow(mPersistData.getToken(), event.getUserId());
				return response;
			}
		};
		submitRequest(railsApiQueEntry);
	}

	@Subscribe
	public void onGrantFollowerPermission(final GrantFollowerPermissionRequest event) {
		System.out.println("inside api repo - making grant follower permission request");
		if (event.getToken() == null)
			event.setToken(mPersistData.getToken());
		GrantFollowerPermissionResponse response = new GrantFollowerPermissionResponse();
		RailsApiQueEntry<GrantFollowerPermissionRequest,GrantFollowerPermissionResponse> railsApiQueEntry = new RailsApiQueEntry<GrantFollowerPermissionRequest,GrantFollowerPermissionResponse>(mBus, mRailsApi, event, response) {

			@Override
			public Response makeRequest() throws RetrofitError {
				Response response = mRailsApiSync.grantFollowerPermission(event.getUserId(), event.getFollowerId(), event);
				return response;
			}
		};
		submitRequest(railsApiQueEntry);
	}

	@Subscribe
	public void onRevokeFollowerPermission(final RevokeFollowerPermissionRequest event) {
		System.out.println("inside api repo - making revoke follower permission request");
		final String token;
		token = event.getToken() == null ? mPersistData.getToken() : event.getToken();
		RevokeFollowerPermissionResponse response = new RevokeFollowerPermissionResponse();
		RailsApiQueEntry<RevokeFollowerPermissionRequest,RevokeFollowerPermissionResponse> railsApiQueEntry = new RailsApiQueEntry<RevokeFollowerPermissionRequest,RevokeFollowerPermissionResponse>(mBus, mRailsApi, event, response) {

			@Override
			public Response makeRequest() throws RetrofitError {
				Response response = mRailsApiSync.revokeFollowerPermission(token, event.getPermission(), event.getUserId(), event.getFollowerId());
				return response;
			}
		};
		submitRequest(railsApiQueEntry);
	}

	@Subscribe
	public void onUpdateUser(final UpdateUserRequest event) {
		System.out.println("inside api repo - making user update  request");
		event.setApiAccessToken(mPersistData.getToken());
		UpdateUserResponse response = new UpdateUserResponse();
		RailsApiQueEntry<UpdateUserRequest,UpdateUserResponse> railsApiQueEntry = new RailsApiQueEntry<UpdateUserRequest,UpdateUserResponse>(mBus, mRailsApi, event, response) {

			@Override
			public Response makeRequest() throws RetrofitError {
				Response response = mRailsApiSync.updateUser(mPersistData.getUserId(), event);
				return response;
			}
		};
		submitRequest(railsApiQueEntry);
	}

	@Subscribe
	public void onCreateDevice(final CreateDeviceRequest event) {
		System.out.println("inside api repo - making create device  request");
		event.setToken(mPersistData.getToken());
		CreateDeviceResponse response = new CreateDeviceResponse();
		RailsApiQueEntry<CreateDeviceRequest,CreateDeviceResponse> railsApiQueEntry = new RailsApiQueEntry<CreateDeviceRequest,CreateDeviceResponse>(mBus, mRailsApi, event, response) {

			@Override
			public Response makeRequest() throws RetrofitError {
				Response response = mRailsApiSync.createDevice(event);
				return response;
			}
		};
		submitRequest(railsApiQueEntry);
	}

	@Subscribe
	public void onPostLocation(final PostLocationRequest event) {
		System.out.println("inside api repo - making post location  request");
		System.out.println(event.getClass().toString());
		event.setToken(mPersistData.getToken());
		if (!event.getGcmRegKey().equals("")) {
			PostLocationResponse response = new PostLocationResponse();
			RailsApiQueEntry<PostLocationRequest,PostLocationResponse> railsApiQueEntry = new RailsApiQueEntry<PostLocationRequest,PostLocationResponse>(mBus, mRailsApi, event, response) {

				@Override
				public Response makeRequest() throws RetrofitError {
					Response response = mRailsApiSync.postLocation(event.getGcmRegKey(), event);
					return response;
				}
			};
			submitRequest(railsApiQueEntry);
		}
	}

	@Subscribe
	public void onPatchLocation(final PatchLocationRequest event) {
		System.out.println("inside api repo - making patch location  request");
		System.out.println(event.getClass().toString());
		event.setToken(mPersistData.getToken());
		if (!event.getGcmRegKey().equals("")) {
			PatchLocationResponse response = new PatchLocationResponse();
			RailsApiQueEntry<PatchLocationRequest,PatchLocationResponse> railsApiQueEntry = new RailsApiQueEntry<PatchLocationRequest,PatchLocationResponse>(mBus, mRailsApi, event, response) {

				@Override
				public Response makeRequest() throws RetrofitError {
					Response response = mRailsApiSync.patchLocation(event.getGcmRegKey(), event);
					return response;
				}
			};
			submitRequest(railsApiQueEntry);
		}
	}

	@Subscribe
	public void onLocationsUpdateRequest(final PushLocationsUpdateRequestRequest event) {
		System.out.println("inside api repo - making locations update  request");
		System.out.println(event.getClass().toString());
		event.setToken(mPersistData.getToken());
		PushLocationsUpdateRequestResponse response = new PushLocationsUpdateRequestResponse();
		RailsApiQueEntry<PushLocationsUpdateRequestRequest,PushLocationsUpdateRequestResponse> railsApiQueEntry = new RailsApiQueEntry<PushLocationsUpdateRequestRequest,PushLocationsUpdateRequestResponse>(mBus, mRailsApi, event, response) {

			@Override
			public Response makeRequest() throws RetrofitError {
				Response response = mRailsApiSync.postPushLocationsUpdateRequest(event);
				return response;
			}
		};
		submitRequest(railsApiQueEntry);
	}

	@Subscribe
	public void onGetMapMarkersRequest(GetMapMarkersRequest event) {
		System.out.println("inside api repo - making get map markers request");
		System.out.println(event.getClass().toString());
		GetMapMarkersResponse response = new GetMapMarkersResponse();
		RailsApiQueEntry<GetMapMarkersRequest,GetMapMarkersResponse> railsApiQueEntry = new RailsApiQueEntry<GetMapMarkersRequest,GetMapMarkersResponse>(mBus, mRailsApi, event, response) {

			@Override
			public Response makeRequest() throws RetrofitError {
				Response response = mRailsApiSync.getMapMarkersRequest(mPersistData.getToken());
				return response;
			}
		};
		submitRequest(railsApiQueEntry);
	}

}

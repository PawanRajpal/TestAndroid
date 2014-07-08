package com.ma.socialmedia;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphPlace;
import com.facebook.model.GraphUser;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.LoginButton;
import com.facebook.widget.ProfilePictureView;
import com.ma.lavasa.R;

public class FBLogin extends Activity{

	private LoginButton loginButton;
	private TextView textView;
	private ProfilePictureView profilePictureView;
	private Button btnPostMessage;
	private Button btnPostPhoto;
	private UiLifecycleHelper uiHelper;
	private GraphUser user;
	private PendingAction pendingAction = PendingAction.NONE;
	private static final String PERMISSION = "publish_actions";
	private boolean canPresentShareDialog;
	private boolean canPresentShareDialogWithPhotos;
	private GraphPlace place;
	private List<GraphUser> tags;
	Intent drawerIntent;

	private enum PendingAction {
		NONE,
		POST_PHOTO,
		POST_STATUS_UPDATE
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(this, callback);
		uiHelper.onCreate(savedInstanceState);
		setContentView(R.layout.fb_login);

		loginButton = (LoginButton)findViewById(R.id.fb_login_button);
		textView = (TextView) findViewById(R.id.textView1);
		profilePictureView = (ProfilePictureView)findViewById(R.id.profilePicture);
		btnPostMessage = (Button)findViewById(R.id.btn_postmessae);
		btnPostPhoto = (Button)findViewById(R.id.btn_postphoto);

		loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));

		loginButton.setSessionStatusCallback(new StatusCallback() {

			@Override
			public void call(Session session, SessionState state, Exception exception) {

				if(session.isOpened()){

					// make request to the /me API
					Request.newMeRequest(session, new Request.GraphUserCallback() {

						// callback after Graph API response with user object
						@Override
						public void onCompleted(GraphUser user, Response response) {
							System.out.println("user-----" + user.toString() + " response----- " + response.toString());

							if (user != null) {
								textView.setText("Hello " + user.getName() + "!" + "\n" + user.asMap().get("email"));
								profilePictureView.setProfileId(user.getId());
								setResult(1);
								finish();
							}
						}
					}).executeAsync();
				}
			}
		});

		btnPostMessage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onClickPostStatusUpdate();
			}
		});

		btnPostPhoto.setVisibility(View.INVISIBLE);
		btnPostPhoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//onClickPostPhoto();
				postPhoto();
			}
		});

		// Can we present the share dialog for regular links?
		canPresentShareDialog = FacebookDialog.canPresentShareDialog(this,
				FacebookDialog.ShareDialogFeature.SHARE_DIALOG);
		// Can we present the share dialog for photos?
		canPresentShareDialogWithPhotos = FacebookDialog.canPresentShareDialog(this,
				FacebookDialog.ShareDialogFeature.PHOTOS);

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}

	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};

	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
		if (state.isOpened()) {
			btnPostMessage.setEnabled(true);
			btnPostPhoto.setEnabled(true);
			System.out.println("Logged in..");
		} else if (state.isClosed()) {
			btnPostMessage.setEnabled(false);
			btnPostPhoto.setEnabled(false);
			System.out.println("Logged Out..");
		}
	}

	@SuppressWarnings("incomplete-switch")
	private void handlePendingAction() {
		PendingAction previouslyPendingAction = pendingAction;
		// These actions may re-set pendingAction if they are still pending, but we assume they
		// will succeed.
		pendingAction = PendingAction.NONE;

		switch (previouslyPendingAction) {
		case POST_PHOTO:
			postPhoto();
			break;
		case POST_STATUS_UPDATE:
			postStatusUpdate();
			break;
		}
	}

	private void postStatusUpdate() {
		if (canPresentShareDialog) {
			FacebookDialog shareDialog = createShareDialogBuilderForLink().build();
			uiHelper.trackPendingDialogCall(shareDialog.present());
		} else if (user != null && hasPublishPermission()) {
			final String message = getString(R.string.status_update, user.getFirstName(), (new Date().toString()));
			Request request = Request
					.newStatusUpdateRequest(Session.getActiveSession(), message, place, tags, new Request.Callback() {
						@Override
						public void onCompleted(Response response) {
							showPublishResult(message, response.getGraphObject(), response.getError());
						}
					});
			request.executeAsync();
		} else {
			pendingAction = PendingAction.POST_STATUS_UPDATE;
		}
	}

	private void postPhoto() {
		Bitmap image = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_launcher);
		if (canPresentShareDialogWithPhotos) {
			FacebookDialog shareDialog = createShareDialogBuilderForPhoto(image).build();
			uiHelper.trackPendingDialogCall(shareDialog.present());
		} else if (hasPublishPermission()) {
			Request request = Request.newUploadPhotoRequest(Session.getActiveSession(), image, new Request.Callback() {
				@Override
				public void onCompleted(Response response) {
					showPublishResult(getString(R.string.photo_post), response.getGraphObject(), response.getError());
				}
			});
			request.executeAsync();
		} else {
			pendingAction = PendingAction.POST_PHOTO;
		}
	}

	private interface GraphObjectWithId extends GraphObject {
		String getId();
	}

	private void onClickPostStatusUpdate() {
		performPublish(PendingAction.POST_STATUS_UPDATE, canPresentShareDialog);
	}

	private FacebookDialog.ShareDialogBuilder createShareDialogBuilderForLink() {
		return new FacebookDialog.ShareDialogBuilder(this)
		.setName("Hello Facebook")
		.setDescription("The 'Hello Facebook' sample application showcases simple Facebook integration")
		.setLink("http://developers.facebook.com/android");
	}

	private void showPublishResult(String message, GraphObject result, FacebookRequestError error) {
		String title = null;
		String alertMessage = null;
		if (error == null) {
			title = getString(R.string.success);
			String id = result.cast(GraphObjectWithId.class).getId();
			alertMessage = getString(R.string.successfully_posted_post, message, id);
		} else {
			title = getString(R.string.error);
			alertMessage = error.getErrorMessage();
		}

		new AlertDialog.Builder(this)
		.setTitle(title)
		.setMessage(alertMessage)
		.setPositiveButton(R.string.ok, null)
		.show();
	}

	private void onClickPostPhoto() {
		performPublish(PendingAction.POST_PHOTO, canPresentShareDialogWithPhotos);
	}

	private FacebookDialog.PhotoShareDialogBuilder createShareDialogBuilderForPhoto(Bitmap... photos) {
		return new FacebookDialog.PhotoShareDialogBuilder(this)
		.addPhotos(Arrays.asList(photos));
	}

	private boolean hasPublishPermission() {
		Session session = Session.getActiveSession();
		return session != null && session.getPermissions().contains("publish_actions");
	}

	private void performPublish(PendingAction action, boolean allowNoSession) {
		Session session = Session.getActiveSession();
		if (session != null) {
			pendingAction = action;
			if (hasPublishPermission()) {
				// We can do the action right away.
				handlePendingAction();
				return;
			} else if (session.isOpened()) {
				// We need to get new permissions, then complete the action when we get called back.
				session.requestNewPublishPermissions(new Session.NewPermissionsRequest(this, PERMISSION));
				return;
			}
		}

		if (allowNoSession) {
			pendingAction = action;
			handlePendingAction();
		}
	}

}

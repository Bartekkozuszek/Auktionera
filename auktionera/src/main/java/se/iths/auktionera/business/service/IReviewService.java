package se.iths.auktionera.business.service;

import se.iths.auktionera.business.model.Review;
import se.iths.auktionera.business.model.ReviewRequest;

public interface IReviewService {

    Review createReview(ReviewRequest reviewRequest, Long id, String userName);
}

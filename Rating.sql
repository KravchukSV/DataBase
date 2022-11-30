-- рейтинг філіалів
SELECT AVG(rate.rate), review.establishment_id 
FROM rate
		INNER JOIN review ON rate.review_id = review.id
GROUP BY review.establishment_id;

-- рейтинг закладів із філіалами
SElECT e2.name, ROUND(e1.avg_r, 2)
FROM(
	SELECT name, AVG(IFNULL(avg_rate, 0)) avg_r, chain_id
	FROM establishment
		LEFT JOIN (	
			SELECT AVG(rate.rate) avg_rate, review.establishment_id 
			FROM rate
			INNER JOIN review ON rate.review_id = review.id
			GROUP BY review.establishment_id) branches
		ON establishment.id = branches.establishment_id
	WHERE chain_id IS NOT NULL
	GROUP BY chain_id
	ORDER BY avg_r DESC) e1
    
    INNER JOIN establishment e2 ON e1.chain_id = e2.id;
    
-- рейтинг закладів без філіалів
SELECT establishment.name, branches.avg_r
FROM establishment
		INNER JOIN (	
			SELECT ROUND(AVG(rate.rate), 2) avg_r, review.establishment_id 
			FROM rate
			INNER JOIN review ON rate.review_id = review.id
			GROUP BY review.establishment_id) branches
		ON establishment.id = branches.establishment_id
WHERE chain_id IS NULL;

-- рейтинг усіх закладів
SElECT e2.name, e1.avg_r
FROM(
	SELECT name, ROUND(AVG(IFNULL(avg_rate, 0)), 2) avg_r, chain_id
	FROM establishment
		LEFT JOIN (	
			SELECT AVG(rate.rate) avg_rate, review.establishment_id 
			FROM rate
			INNER JOIN review ON rate.review_id = review.id
			GROUP BY review.establishment_id) branches
		ON establishment.id = branches.establishment_id
	WHERE chain_id IS NOT NULL
	GROUP BY chain_id
	ORDER BY avg_r DESC) e1
    
    INNER JOIN establishment e2 ON e1.chain_id = e2.id
    
UNION

SELECT establishment.name,  branches.avg_r
FROM establishment
		INNER JOIN (	
			SELECT ROUND(AVG(rate.rate), 2) avg_r, review.establishment_id 
			FROM rate
			INNER JOIN review ON rate.review_id = review.id
			GROUP BY review.establishment_id) branches
		ON establishment.id = branches.establishment_id
WHERE chain_id IS NULL

ORDER BY avg_r DESC;
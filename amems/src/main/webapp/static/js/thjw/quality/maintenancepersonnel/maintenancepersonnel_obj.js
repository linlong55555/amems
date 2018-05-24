	
	/** 维修档案人员对象 */
	var personnel = (function(){
		
		// 教育经历
		var educations = [];
		// 外语水平
		var languages = [];
		// 聘任职称
		var titles = [];
		// 工作履历
		var workExperiences = [];
		// 岗位职务
		var posts = [];
		// 技术履历
		var technicalExperiences = [];
		// 维修执照
		var maintenanceLicenses = [];
		// 机型执照
		var typeLicenses = [];
		// 维修技术等级升级记录
		var grades = [];
		// 培训记录
		var trainings = [];
		// 业务考核记录
		var businessAssessments = [];
		// 学术成就
		var scholarships = [];
		// 嘉奖记录
		var citationRecords = [];
		// 事故征候情况
		var incidentSituations = [];
		// 负有责任的不安全事件
		var unsafeIncidents = [];
		// 不诚信行为
		var dishonestBehaviors = [];
		// 需删除附件
		var delAttachments = [];
		// 基本信息附件
		var attachments = [];
		
		return {
			
			/*
			 * 教育经历
			 */
			education : {
				get : function(rowid){
					var result;
					for (var i = 0; i < educations.length; i++) {
			            if (educations[i].rowid == rowid) {
			            	result = educations[i];
			            }
			        }
					return result;
				},
				getAll : function(){
					return educations;
				},
				merge : function(education){
					var insert = true;
					for(var i = 0; i < educations.length; i++){
						if(educations[i].rowid == education.rowid){
							educations[i] = education;
							insert = false;
						}
					}
					if(insert){
						educations.push(education);
					}
				},
				remove : function(rowid){
					for (var i = 0; i < educations.length; i++) {
			            if (educations[i].rowid == rowid) {
			            	educations.splice(i, 1);
			            }
			        }
				} 
			},
			
			/*
			 * 外语水平
			 */
			language : {
				get : function(rowid){
					var result;
					for (var i = 0; i < languages.length; i++) {
			            if (languages[i].rowid == rowid) {
			            	result = languages[i];
			            }
			        }
					return result;
				},
				getAll : function(){
					return languages;
				},
				merge : function(language){
					var insert = true;
					for(var i = 0; i < languages.length; i++){
						if(languages[i].rowid == language.rowid){
							languages[i] = language;
							insert = false;
						}
					}
					if(insert){
						languages.push(language);
					}
				},
				remove : function(rowid){
					for (var i = 0; i < languages.length; i++) {
			            if (languages[i].rowid == rowid) {
			            	languages.splice(i, 1);
			            }
			        }
				} 
			},
			
			/*
			 * 聘任职称
			 */
			title : {
				get : function(rowid){
					var result;
					for (var i = 0; i < titles.length; i++) {
			            if (titles[i].rowid == rowid) {
			            	result = titles[i];
			            }
			        }
					return result;
				},
				getAll : function(){
					return titles;
				},
				merge : function(title){
					var insert = true;
					for(var i = 0; i < titles.length; i++){
						if(titles[i].rowid == title.rowid){
							titles[i] = title;
							insert = false;
						}
					}
					if(insert){
						titles.push(title);
					}
				},
				remove : function(rowid){
					for (var i = 0; i < titles.length; i++) {
			            if (titles[i].rowid == rowid) {
			            	titles.splice(i, 1);
			            }
			        }
				} 
			},
			
			/*
			 * 工作履历
			 */
			workExperience : {
				get : function(rowid){
					var result;
					for (var i = 0; i < workExperiences.length; i++) {
			            if (workExperiences[i].rowid == rowid) {
			            	result = workExperiences[i];
			            }
			        }
					return result;
				},
				getAll : function(){
					return workExperiences;
				},
				merge : function(workExperience){
					var insert = true;
					for(var i = 0; i < workExperiences.length; i++){
						if(workExperiences[i].rowid == workExperience.rowid){
							workExperiences[i] = workExperience;
							insert = false;
						}
					}
					if(insert){
						workExperiences.push(workExperience);
					}
				},
				remove : function(rowid){
					for (var i = 0; i < workExperiences.length; i++) {
			            if (workExperiences[i].rowid == rowid) {
			            	workExperiences.splice(i, 1);
			            }
			        }
				} 
			},
			
			/*
			 * 岗位职务
			 */
			post : {
				get : function(rowid){
					var result;
					for (var i = 0; i < posts.length; i++) {
			            if (posts[i].rowid == rowid) {
			            	result = posts[i];
			            }
			        }
					return result;
				},
				getAll : function(){
					return posts;
				},
				merge : function(post){
					var insert = true;
					for(var i = 0; i < posts.length; i++){
						if(posts[i].rowid == post.rowid){
							posts[i] = post;
							insert = false;
						}
					}
					if(insert){
						posts.push(post);
					}
				},
				remove : function(rowid){
					for (var i = 0; i < posts.length; i++) {
			            if (posts[i].rowid == rowid) {
			            	posts.splice(i, 1);
			            }
			        }
				} 
			},
			
			/*
			 * 技术履历
			 */
			technicalExperience : {
				get : function(rowid){
					var result;
					for (var i = 0; i < technicalExperiences.length; i++) {
			            if (technicalExperiences[i].rowid == rowid) {
			            	result = technicalExperiences[i];
			            }
			        }
					return result;
				},
				getAll : function(){
					return technicalExperiences;
				},
				merge : function(technicalExperience){
					var insert = true;
					for(var i = 0; i < technicalExperiences.length; i++){
						if(technicalExperiences[i].rowid == technicalExperience.rowid){
							technicalExperiences[i] = technicalExperience;
							insert = false;
						}
					}
					if(insert){
						technicalExperiences.push(technicalExperience);
					}
				},
				remove : function(rowid){
					for (var i = 0; i < technicalExperiences.length; i++) {
			            if (technicalExperiences[i].rowid == rowid) {
			            	technicalExperiences.splice(i, 1);
			            }
			        }
				} 
			},
			
			/*
			 * 维修执照
			 */
			maintenanceLicense : {
				get : function(rowid){
					var result;
					for (var i = 0; i < maintenanceLicenses.length; i++) {
			            if (maintenanceLicenses[i].rowid == rowid) {
			            	result = maintenanceLicenses[i];
			            }
			        }
					return result;
				},
				getAll : function(){
					return maintenanceLicenses;
				},
				merge : function(maintenanceLicense){
					var insert = true;
					for(var i = 0; i < maintenanceLicenses.length; i++){
						if(maintenanceLicenses[i].rowid == maintenanceLicense.rowid){
							maintenanceLicenses[i] = maintenanceLicense;
							insert = false;
						}
					}
					if(insert){
						maintenanceLicenses.push(maintenanceLicense);
					}
				},
				remove : function(rowid){
					for (var i = 0; i < maintenanceLicenses.length; i++) {
			            if (maintenanceLicenses[i].rowid == rowid) {
			            	maintenanceLicenses.splice(i, 1);
			            }
			        }
				} 
			},
			
			/*
			 * 机型执照
			 */
			typeLicense : {
				get : function(rowid){
					var result;
					for (var i = 0; i < typeLicenses.length; i++) {
			            if (typeLicenses[i].rowid == rowid) {
			            	result = typeLicenses[i];
			            }
			        }
					return result;
				},
				getAll : function(){
					return typeLicenses;
				},
				merge : function(typeLicense){
					var insert = true;
					for(var i = 0; i < typeLicenses.length; i++){
						if(typeLicenses[i].rowid == typeLicense.rowid){
							typeLicenses[i] = typeLicense;
							insert = false;
						}
					}
					if(insert){
						typeLicenses.push(typeLicense);
					}
				},
				remove : function(rowid){
					for (var i = 0; i < typeLicenses.length; i++) {
			            if (typeLicenses[i].rowid == rowid) {
			            	typeLicenses.splice(i, 1);
			            }
			        }
				} 
			},
			
			/*
			 * 维修技术等级升级记录
			 */
			grade : {
				get : function(rowid){
					var result;
					for (var i = 0; i < grades.length; i++) {
			            if (grades[i].rowid == rowid) {
			            	result = grades[i];
			            }
			        }
					return result;
				},
				getAll : function(){
					return grades;
				},
				merge : function(grade){
					var insert = true;
					for(var i = 0; i < grades.length; i++){
						if(grades[i].rowid == grade.rowid){
							grades[i] = grade;
							insert = false;
						}
					}
					if(insert){
						grades.push(grade);
					}
				},
				remove : function(rowid){
					for (var i = 0; i < grades.length; i++) {
			            if (grades[i].rowid == rowid) {
			            	grades.splice(i, 1);
			            }
			        }
				} 
			},

			/*
			 * 培训记录
			 */
			training : {
				get : function(rowid){
					var result;
					for (var i = 0; i < trainings.length; i++) {
			            if (trainings[i].rowid == rowid) {
			            	result = trainings[i];
			            }
			        }
					return result;
				},
				getAll : function(){
					return trainings;
				},
				merge : function(training){
					var insert = true;
					for(var i = 0; i < trainings.length; i++){
						if(trainings[i].rowid == training.rowid){
							trainings[i] = training;
							insert = false;
						}
					}
					if(insert){
						trainings.push(training);
					}
				},
				remove : function(rowid){
					for (var i = 0; i < trainings.length; i++) {
			            if (trainings[i].rowid == rowid) {
			            	trainings.splice(i, 1);
			            }
			        }
				} 
			},

			/*
			 * 业务考核记录
			 */
			businessAssessment : {
				get : function(rowid){
					var result;
					for (var i = 0; i < businessAssessments.length; i++) {
			            if (businessAssessments[i].rowid == rowid) {
			            	result = businessAssessments[i];
			            }
			        }
					return result;
				},
				getAll : function(){
					return businessAssessments;
				},
				merge : function(businessAssessment){
					var insert = true;
					for(var i = 0; i < businessAssessments.length; i++){
						if(businessAssessments[i].rowid == businessAssessment.rowid){
							businessAssessments[i] = businessAssessment;
							insert = false;
						}
					}
					if(insert){
						businessAssessments.push(businessAssessment);
					}
				},
				remove : function(rowid){
					for (var i = 0; i < businessAssessments.length; i++) {
			            if (businessAssessments[i].rowid == rowid) {
			            	businessAssessments.splice(i, 1);
			            }
			        }
				} 
			},
			

			/*
			 * 学术成就 
			 */
			scholarship : {
				get : function(rowid){
					var result;
					for (var i = 0; i < scholarships.length; i++) {
			            if (scholarships[i].rowid == rowid) {
			            	result = scholarships[i];
			            }
			        }
					return result;
				},
				getAll : function(){
					return scholarships;
				},
				merge : function(scholarship){
					var insert = true;
					for(var i = 0; i < scholarships.length; i++){
						if(scholarships[i].rowid == scholarship.rowid){
							scholarships[i] = scholarship;
							insert = false;
						}
					}
					if(insert){
						scholarships.push(scholarship);
					}
				},
				remove : function(rowid){
					for (var i = 0; i < scholarships.length; i++) {
			            if (scholarships[i].rowid == rowid) {
			            	scholarships.splice(i, 1);
			            }
			        }
				} 
			},

			/*
			 * 嘉奖记录
			 */
			citationRecord : {
				get : function(rowid){
					var result;
					for (var i = 0; i < citationRecords.length; i++) {
			            if (citationRecords[i].rowid == rowid) {
			            	result = citationRecords[i];
			            }
			        }
					return result;
				},
				getAll : function(){
					return citationRecords;
				},
				merge : function(citationRecord){
					var insert = true;
					for(var i = 0; i < citationRecords.length; i++){
						if(citationRecords[i].rowid == citationRecord.rowid){
							citationRecords[i] = citationRecord;
							insert = false;
						}
					}
					if(insert){
						citationRecords.push(citationRecord);
					}
				},
				remove : function(rowid){
					for (var i = 0; i < citationRecords.length; i++) {
			            if (citationRecords[i].rowid == rowid) {
			            	citationRecords.splice(i, 1);
			            }
			        }
				} 
			},
			

			/*
			 * 事故征候情况
			 */
			incidentSituation : {
				get : function(rowid){
					var result;
					for (var i = 0; i < incidentSituations.length; i++) {
			            if (incidentSituations[i].rowid == rowid) {
			            	result = incidentSituations[i];
			            }
			        }
					return result;
				},
				getAll : function(){
					return incidentSituations;
				},
				merge : function(incidentSituation){
					var insert = true;
					for(var i = 0; i < incidentSituations.length; i++){
						if(incidentSituations[i].rowid == incidentSituation.rowid){
							incidentSituations[i] = incidentSituation;
							insert = false;
						}
					}
					if(insert){
						incidentSituations.push(incidentSituation);
					}
				},
				remove : function(rowid){
					for (var i = 0; i < incidentSituations.length; i++) {
			            if (incidentSituations[i].rowid == rowid) {
			            	incidentSituations.splice(i, 1);
			            }
			        }
				} 
			},

			/*
			 * 负有责任的不安全事件
			 */
			unsafeIncident : {
				get : function(rowid){
					var result;
					for (var i = 0; i < unsafeIncidents.length; i++) {
			            if (unsafeIncidents[i].rowid == rowid) {
			            	result = unsafeIncidents[i];
			            }
			        }
					return result;
				},
				getAll : function(){
					return unsafeIncidents;
				},
				merge : function(unsafeIncident){
					var insert = true;
					for(var i = 0; i < unsafeIncidents.length; i++){
						if(unsafeIncidents[i].rowid == unsafeIncident.rowid){
							unsafeIncidents[i] = unsafeIncident;
							insert = false;
						}
					}
					if(insert){
						unsafeIncidents.push(unsafeIncident);
					}
				},
				remove : function(rowid){
					for (var i = 0; i < unsafeIncidents.length; i++) {
			            if (unsafeIncidents[i].rowid == rowid) {
			            	unsafeIncidents.splice(i, 1);
			            }
			        }
				} 
			},

			/*
			 * 不诚信行为
			 */
			dishonestBehavior : {
				get : function(rowid){
					var result;
					for (var i = 0; i < dishonestBehaviors.length; i++) {
			            if (dishonestBehaviors[i].rowid == rowid) {
			            	result = dishonestBehaviors[i];
			            }
			        }
					return result;
				},
				getAll : function(){
					return dishonestBehaviors;
				},
				merge : function(dishonestBehavior){
					var insert = true;
					for(var i = 0; i < dishonestBehaviors.length; i++){
						if(dishonestBehaviors[i].rowid == dishonestBehavior.rowid){
							dishonestBehaviors[i] = dishonestBehavior;
							insert = false;
						}
					}
					if(insert){
						dishonestBehaviors.push(dishonestBehavior);
					}
				},
				remove : function(rowid){
					for (var i = 0; i < dishonestBehaviors.length; i++) {
			            if (dishonestBehaviors[i].rowid == rowid) {
			            	dishonestBehaviors.splice(i, 1);
			            }
			        }
				} 
			},
		
			/*
			 * 附件
			 */
			attachment : {
				findByRowid : function(rowid){
					var result = [];
					if(rowid == $("#personnelId").val()){
						result = attachments;
					}
					for (var i = 0; i < educations.length; i++) {
			            if (educations[i].rowid == rowid) {
			            	result = educations[i].attachments;
			            }
			        }
					for (var i = 0; i < languages.length; i++) {
						if (languages[i].rowid == rowid) {
			            	result = languages[i].attachments;
			            }
			        }
					for (var i = 0; i < titles.length; i++) {
						if (titles[i].rowid == rowid) {
			            	result = titles[i].attachments;
			            }
			        }
					for (var i = 0; i < workExperiences.length; i++) {
						if (workExperiences[i].rowid == rowid) {
			            	result = workExperiences[i].attachments;
			            }
			        }
					for (var i = 0; i < posts.length; i++) {
						if (posts[i].rowid == rowid) {
			            	result = posts[i].attachments;
			            }
			        }
					for (var i = 0; i < technicalExperiences.length; i++) {
						if (technicalExperiences[i].rowid == rowid) {
			            	result = technicalExperiences[i].attachments;
			            }
			        }
					for (var i = 0; i < maintenanceLicenses.length; i++) {
						if (maintenanceLicenses[i].rowid == rowid) {
			            	result = maintenanceLicenses[i].attachments;
			            }
			        }
					for (var i = 0; i < typeLicenses.length; i++) {
						if (typeLicenses[i].rowid == rowid) {
			            	result = typeLicenses[i].attachments;
			            }
			        }
					for (var i = 0; i < grades.length; i++) {
						if (grades[i].rowid == rowid) {
			            	result = grades[i].attachments;
			            }
			        }
					for (var i = 0; i < trainings.length; i++) {
						if (trainings[i].rowid == rowid) {
			            	result = trainings[i].attachments;
			            }
			        }
					for (var i = 0; i < businessAssessments.length; i++) {
						if (businessAssessments[i].rowid == rowid) {
			            	result = businessAssessments[i].attachments;
			            }
			        }
					for (var i = 0; i < scholarships.length; i++) {
						if (scholarships[i].rowid == rowid) {
			            	result = scholarships[i].attachments;
			            }
			        }
					for (var i = 0; i < citationRecords.length; i++) {
						if (citationRecords[i].rowid == rowid) {
			            	result = citationRecords[i].attachments;
			            }
			        }
					for (var i = 0; i < incidentSituations.length; i++) {
						if (incidentSituations[i].rowid == rowid) {
			            	result = incidentSituations[i].attachments;
			            }
			        }
					for (var i = 0; i < unsafeIncidents.length; i++) {
						if (unsafeIncidents[i].rowid == rowid) {
			            	result = unsafeIncidents[i].attachments;
			            }
			        }
					for (var i = 0; i < dishonestBehaviors.length; i++) {
						if (dishonestBehaviors[i].rowid == rowid) {
			            	result = dishonestBehaviors[i].attachments;
			            }
			        }
					return result;
				},
				remove : function(attachment){
					delAttachments.push(attachment);
				},
				getAllRemoveAttachments : function(){
					return delAttachments;
				},
				getBasicAttachments : function(){
					return attachments;
				},
				updateBasicAttachments : function(list){
					attachments = list;
				},
				getAll : function(){
					var result = [];
					var temp = educations.concat(languages).concat(titles).concat(workExperiences)
						.concat(posts).concat(technicalExperiences).concat(maintenanceLicenses)
						.concat(typeLicenses).concat(grades).concat(trainings).concat(businessAssessments)
						.concat(scholarships).concat(citationRecords).concat(incidentSituations)
						.concat(unsafeIncidents).concat(dishonestBehaviors);
					result = result.concat(attachments);
					for(var i = 0; i < temp.length; i++){
						result = result.concat(temp[i].attachments);
					}
					return result;
				}
			},
			updateData : function(data){
				educations = data.educations;
				languages = data.languages;
				titles = data.titles;
				workExperiences = data.workExperiences;
				posts = data.posts;
				technicalExperiences = data.technicalExperiences;
				maintenanceLicenses = data.maintenanceLicenses;
				typeLicenses = data.typeLicenses;
				grades = data.grades;
				trainings = data.trainings;
				businessAssessments = data.businessAssessments;
				scholarships = data.scholarships;
				citationRecords = data.citationRecords;
				incidentSituations = data.incidentSituations;
				unsafeIncidents = data.unsafeIncidents;
				dishonestBehaviors = data.dishonestBehaviors;
				attachments = data.attachments;
				delAttachments = [];
				addRowid(educations);
				addRowid(languages);
				addRowid(titles);
				addRowid(workExperiences);
				addRowid(posts);
				addRowid(technicalExperiences);
				addRowid(maintenanceLicenses);
				addRowid(typeLicenses);
				addRowid(grades);
				addRowid(trainings);
				addRowid(businessAssessments);
				addRowid(scholarships);
				addRowid(citationRecords);
				addRowid(incidentSituations);
				addRowid(unsafeIncidents);
				addRowid(dishonestBehaviors);
				
				function addRowid(list){
					for (var i = 0; i < list.length; i++) {
						list[i].rowid = list[i].id;
						if(list[i].ksrq){
							list[i].ksrq = (list[i].ksrq||"").substr(0,10);
						}
						if(list[i].jsrq){
							list[i].jsrq = (list[i].jsrq||"").substr(0,10);
						}
						if(list[i].prrq){
							list[i].prrq = (list[i].prrq||"").substr(0,10);
						}
						if(list[i].prqx){
							list[i].prqx = (list[i].prqx||"").substr(0,10);
						}
						if(list[i].rq){
							list[i].rq = (list[i].rq||"").substr(0,10);
						}
						if(list[i].bfrq){
							list[i].bfrq = (list[i].bfrq||"").substr(0,10);
						}
						if(list[i].yxqKs){
							list[i].yxqKs = (list[i].yxqKs||"").substr(0,10);
						}
						if(list[i].yxqJs){
							list[i].yxqJs = (list[i].yxqJs||"").substr(0,10);
						}
						if(list[i].xcpxrq){
							list[i].xcpxrq = (list[i].xcpxrq||"").substr(0,10);
						}
						if(list[i].sjKsrq){
							list[i].sjKsrq = (list[i].sjKsrq||"").substr(0,10);
						}
						if(list[i].sjJsrq){
							list[i].sjJsrq = (list[i].sjJsrq||"").substr(0,10);
						}
						if(list[i].jcrq){
							list[i].jcrq = (list[i].jcrq||"").substr(0,10);
						}
			        }
				}
			}
		}
	})();
	

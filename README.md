# capstone3333
Models
All models I have worked on:

Company 
Event
MaintenanceExpert
Admin
Fine

Repository
AdminRepository.

CompanyRepository.

CourseRepository:
I was responsible  for writing the filterCourses Endpoint .

EventRepository:
I wrote whole the repository except findAllByDate endpoint.

FineRepository.

MaintenanceExpertRepository:
i wrote whole the repository except findMaintenanceExpertByName endpoint.

MotorcycleRepository:
i wrote findMotorcycleByOwnerId endpoint .

RentingRepository:
i wrote existsByMotorcycleAndDateRange and existsByMotorcycleAndDateRangeExcludingRequest in RentingRepository.

RentingRequestRepository:
	I wrote findOverdueRentals endpoint.










Service
All services I have worked on:

Company:
(All CRUD).

Event:
(All CRUD).

MaintenanceRequest :
I was responsible for writing the getMaintenanceHistory Endpoint.

Admin:
(All CRUD).

MaintenanceExpert:
(All CRUD).

FineService :
(All Methods in the Service)

CourseService:
I was responsible  for writing the filterCourses Endpoint in CourseService.

RentingRequestService:
I was responsible  for writing the All Endpoints in RentingRequestService except extendRental and getAllRentingRequests Endpoint.

Controller
AdminController
EventController
CompanyController
FineController
MaintenanceExpertController
RentingRequestController :
I wrote Whole controller except extendRental.




DTO
AdminDTO
CompanyDTO
EventDTO
FineDTO
MaintenanceExpertDTO
MaintenanceRequestHistoryDTO
In DTO
FineDTO_In



Endpoints:
in admin Service
public void approveCompany(Integer adminId,Integer companyId){
   Admin admin = adminRepository.findAdminById(adminId);
   Company company = companyRepository.findCompanyById(companyId);
   if(admin==null||company==null){
       throw new ApiException("Can't approve");
   }
   if(!company.getIsApproved()){
       company.setIsApproved(true);
       companyRepository.save(company);
   }else throw new ApiException("Company is already approved");




}


//Raghad
public void approveExpert(Integer adminId,Integer expertId){
   MaintenanceExpert expert = maintenanceExpertRepository.findMaintenanceExpertById(expertId);
   Admin admin = adminRepository.findAdminById(adminId);
   if(expert ==null|| admin==null){
       throw new ApiException("Can't approve");
   }


   if(!expert.getIsApproved()){
       expert.setIsApproved(true);
       maintenanceExpertRepository.save(expert);
   }else throw new ApiException("Maintenance Expert is already approved");
}



 filterCourses in Course Service 
//Raghad
   public List<CourseDTO> filterCourses(Double minPrice, Double maxPrice, Integer minDuration, Integer maxDuration) {
       // Fetch filtered courses from the repository
       List<Course> courses = courseRepository.filterCourses(minPrice, maxPrice, minDuration, maxDuration);


       // Map courses to CourseDTOs
       return courses.stream().map(course -> new CourseDTO(
               course.getName(),
               course.getDescription(),
               course.getPrice(),
               course.getDuration() // Include trainer name in the DTO
       )).collect(Collectors.toList());
   }


in Fine Service 


   //Raghad
   //  Fetch all fines by user ID
   public List<FineDTO> getAllFineByUserId(Integer userId){
       List<Fine> fines = fineRepository.findFineByUserId(userId);
       List<FineDTO> fineDTOS=new ArrayList<>();


       for(Fine fine:fines){
           FineDTO fineDTO = new FineDTO(fine.getDescription(),fine.getAmount(),fine.getIsPaid());
           fineDTOS.add(fineDTO);
       }
       return fineDTOS;
   }


//Raghad
// Scheduled job to impose late return penalties
@Scheduled(cron = "0 0 0 * * *") // Run daily at midnight
//@Scheduled(fixedRate = 60000)
public void imposeLateReturnPenalties() {
   // Fetch overdue renting requests where the bike is not yet returned
   List<RentingRequest> overdueRequests = rentingRequestRepository.findOverdueRentals(LocalDate.now());


   for (RentingRequest rentingRequest : overdueRequests) {
       // Skip fine calculation if the bike has been returned
       if (Boolean.TRUE.equals(rentingRequest.getIsReturned())) {
           continue;
       }


       LocalDate endDate = rentingRequest.getEndDate();
       LocalDate currentDate = LocalDate.now();


       // Calculate late days
       long lateDays = java.time.temporal.ChronoUnit.DAYS.between(endDate, currentDate);


       if (lateDays > 0) {
           // Calculate fine amount
           Renting renting = rentingRequest.getRenting();
           if (renting == null) {
               System.err.println("Renting details not found for RentingRequest ID " + rentingRequest.getId());
               continue; // Skip this request
           }


           Double fineAmount = lateDays * renting.getPricePerDay();


           // Update or create the Fine entity
           Fine fine = rentingRequest.getFine();
           if (fine == null) {
               fine = new Fine();
               fine.setDescription("Late return penalty for " + lateDays + " days");
               fine.setAmount(fineAmount);
               fine.setUser(rentingRequest.getUser());
               fine.setRentingRequest(rentingRequest);
               fine.setIsPaid(false);
               rentingRequest.setFine(fine);
           } else {
               fine.setAmount(fineAmount); // Update the existing fine
           }


           fineRepository.save(fine);
           System.out.println("Updated fine for RentingRequest ID " + rentingRequest.getId() +
                   ": $" + fineAmount + " for " + lateDays + " days late.");
       }
   }
}
   //Raghad
   // Method to mark a bike as returned
   public void markBikeAsReturned(Integer rentingRequestId) {
       RentingRequest rentingRequest = rentingRequestRepository.findRentingRequestById(rentingRequestId);
         if(rentingRequest==null){
           throw new ApiException("Renting Request not found");}
       Owner owner = ownerRepository.findOwnerById(rentingRequest.getRenting().getOwner().getId());
         if(owner ==null){
             throw new ApiException("Just owner can mark a bike as returned");
         }




       // Update the isReturned status
       rentingRequest.setIsReturned(true);
       rentingRequestRepository.save(rentingRequest);


       System.out.println("Bike marked as returned for RentingRequest ID " + rentingRequestId);
   }




   public long getNumberOfFinesByUserId(Integer userId) {
       // Fetch the count of fines by user ID
       return fineRepository.countFinesByUserId(userId);
   }


//Raghad
//fine payment feature
   public void payFine(Integer fineId) {
       // Step 1: Fetch the fine
       Fine fine = fineRepository.findById(fineId)
               .orElseThrow(() -> new ApiException("Fine not found"));


       // Step 2: Check if the fine is already paid
       if (Boolean.TRUE.equals(fine.getIsPaid())) {
           throw new ApiException("This fine has already been paid");
       }


       // Step 3: Mark the fine as paid
       fine.setIsPaid(true);
       fineRepository.save(fine);


   }


   //Raghad
   public List<FineDTO> getUnpaidFinesByUserId(Integer userId) {
       List<Fine> fines = fineRepository.findUnpaidFinesByUserId(userId);
       List<FineDTO> fineDTOS=new ArrayList<>();


       for(Fine fine:fines){
           FineDTO fineDTO = new FineDTO(fine.getDescription(),fine.getAmount(),fine.getIsPaid());
           fineDTOS.add(fineDTO);
       }
       return fineDTOS;


   }


in RentingRequestService i do this methods( getAllRentingRequests - addRentingRequest-calculateTotalCost-updateRentingRequest)


in RentingRequestRepository
//Raghad
@Query("SELECT r FROM RentingRequest r WHERE r.endDate < :currentDate")
List<RentingRequest> findOverdueRentals(@Param("currentDate") LocalDate currentDate);

in RentingRepository

  // Query to check availability of motorcycle for new renting requests
//Raghad
   @Query("SELECT COUNT(r) > 0 FROM RentingRequest r " +
           "WHERE r.renting.motorcycleId = :motorcycleId " +
           "AND (:startDate BETWEEN r.startDate AND r.endDate " +
           "OR :endDate BETWEEN r.startDate AND r.endDate)")
   boolean existsByMotorcycleAndDateRange(
           @Param("motorcycleId") Integer motorcycleId,
           @Param("startDate") LocalDate startDate,
           @Param("endDate") LocalDate endDate
   );
  
//Raghad
   // Query to check availability of motorcycle for updates, excluding the current request
   @Query("SELECT COUNT(r) > 0 FROM RentingRequest r " +
           "WHERE r.renting.motorcycleId = :motorcycleId " +
           "AND r.id <> :rentingRequestId " + // Exclude the current renting request
           "AND (:startDate BETWEEN r.startDate AND r.endDate " +
           "OR :endDate BETWEEN r.startDate AND r.endDate)")
   boolean existsByMotorcycleAndDateRangeExcludingRequest(
           @Param("motorcycleId") Integer motorcycleId,
           @Param("startDate") LocalDate startDate,
           @Param("endDate") LocalDate endDate,
           @Param("rentingRequestId") Integer rentingRequestId
   );

in MotorcycleRepository
//Raghad
List<Motorcycle> findMotorcycleByOwnerId(Integer owenrId);

in MaintenanceRequestRepository
//Raghad
@Query("SELECT m FROM MaintenanceRequest m WHERE m.expert_name = :expertName AND m.pickupDate > :today")
List<MaintenanceRequest> findUpcomingRequestsByExpert(String expertName,  LocalDate today);





in  FineRepository
List<Fine> findFineByUserId(Integer userId);


@Query("SELECT COUNT(f) FROM Fine f WHERE f.user.id = :userId")
long countFinesByUserId(@Param("userId") Integer userId);


@Query("SELECT f FROM Fine f WHERE f.user.id = :userId AND f.isPaid = false")
List<Fine> findUnpaidFinesByUserId(@Param("userId") Integer userId);



in CourseRepository


//Raghad
   @Query("SELECT c FROM Course c " +
           "WHERE (:minPrice IS NULL OR c.price >= :minPrice) " +
           "AND (:maxPrice IS NULL OR c.price <= :maxPrice) " +
           "AND (:minDuration IS NULL OR c.duration >= :minDuration) " +
           "AND (:maxDuration IS NULL OR c.duration <= :maxDuration) " )
   List<Course> filterCourses(
           @Param("minPrice") Double minPrice,
           @Param("maxPrice") Double maxPrice,
           @Param("minDuration") Integer minDuration,
           @Param("maxDuration") Integer maxDuration
   );

in BookingCourseRepository
//Raghad
   @Query("SELECT COUNT(b) > 0 FROM BookingCourse b " +
           "WHERE b.course.owner.id = :ownerId " +
           "AND (:startDate BETWEEN b.courseStartDate AND b.courseEndDate " +
           "OR :endDate BETWEEN b.courseStartDate AND b.courseEndDate)")
   boolean isTrainerUnavailable(
           @Param("ownerId") Integer ownerId,
           @Param("startDate") LocalDate startDate,
           @Param("endDate") LocalDate endDate
   );


